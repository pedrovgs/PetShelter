import json
import os
import re
import sys
import time
import uuid
from urllib.parse import urljoin

# Ensure real-time output (no buffering)
sys.stdout.reconfigure(line_buffering=True)

import requests
from bs4 import BeautifulSoup

BASE_URL = "https://www.protectoramalaga.com/"
OUTPUT_DIR = os.path.join(os.path.dirname(__file__), "output")
DATA_FILE = os.path.join(OUTPUT_DIR, "animals.json")

HEADERS = {
    "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"
}

REQUEST_DELAY = 1  # seconds between requests to be polite


def fetch_page(url):
    time.sleep(REQUEST_DELAY)
    print(f"  Fetching: {url}")
    response = requests.get(url, headers=HEADERS, timeout=30)
    response.raise_for_status()
    soup = BeautifulSoup(response.content, "html.parser")
    # Extract <base href> if present, used to resolve relative URLs
    base_tag = soup.find("base", href=True)
    soup._base_url = base_tag["href"] if base_tag else url
    return soup


def resolve_url(soup, relative_href):
    """Resolve a relative href using the page's <base> tag or BASE_URL."""
    return urljoin(getattr(soup, "_base_url", BASE_URL), relative_href)


def get_listing_urls(listing_url):
    """Extract individual animal detail URLs from a listing page."""
    soup = fetch_page(listing_url)
    urls = []
    for link in soup.find_all("a", href=True):
        href = link["href"]
        if "perro-en-adopcion/" in href and href != listing_url:
            full_url = resolve_url(soup, href)
            if full_url not in urls:
                urls.append(full_url)
    return urls


def get_all_animal_urls():
    """Collect all animal detail URLs from dogs (pages 1-10) and cats (page 1)."""
    dog_urls = []
    cat_urls = []

    # Dogs: 10 pages
    print("Collecting dog listing pages...")
    for page in range(1, 11):
        listing_url = urljoin(BASE_URL, f"perros-en-adopcion/{page}/")
        urls = get_listing_urls(listing_url)
        if not urls:
            print(f"  No dogs found on page {page}, stopping.")
            break
        dog_urls.extend(urls)
        print(f"  Page {page}: found {len(urls)} dogs")

    # Cats: check multiple pages
    print("Collecting cat listing pages...")
    for page in range(1, 5):
        listing_url = urljoin(BASE_URL, f"gatos-en-adopcion/{page}/")
        try:
            urls = get_listing_urls(listing_url)
        except requests.HTTPError:
            break
        if not urls:
            break
        cat_urls.extend(urls)
        print(f"  Page {page}: found {len(urls)} cats")

    return dog_urls, cat_urls


def parse_animal_detail(url, animal_type):
    """Parse an individual animal detail page."""
    soup = fetch_page(url)

    animal = {
        "id": str(uuid.uuid4()),
        "animal_type": animal_type,
        "name": None,
        "sex": None,
        "breed": None,
        "size": None,
        "reference": None,
        "age": None,
        "description": None,
        "images": [],
        "videos": [],
        "source_url": url,
    }

    # Name: first h2 on the page
    h2 = soup.find("h2")
    if h2:
        animal["name"] = h2.get_text(strip=True)

    # Fields from h4 tags
    field_mapping = {
        "sexo": "sex",
        "raza": "breed",
        "tamaño": "size",
        "ref": "reference",
        "edad aproximada": "age",
    }

    for h4 in soup.find_all("h4"):
        text = h4.get_text(strip=True)
        for spanish_key, english_key in field_mapping.items():
            if text.lower().startswith(spanish_key):
                # Extract value after the colon
                parts = text.split(":", 1)
                if len(parts) == 2:
                    animal[english_key] = parts[1].strip()
                break

    # Description: collect p tags that contain descriptive text
    # Skip p tags that are part of navigation, headers, or very short
    description_parts = []
    for p in soup.find_all("p"):
        text = p.get_text(strip=True)
        # Filter out navigation, footer, and short non-descriptive paragraphs
        if len(text) > 50 and not text.startswith("Página") and "cookie" not in text.lower():
            description_parts.append(text)
    if description_parts:
        animal["description"] = "\n\n".join(description_parts)

    # Images: links to high-res images in imagenes/catalogos/high/
    for link in soup.find_all("a", href=True):
        href = link["href"]
        if "imagenes/catalogos/high/" in href:
            image_url = resolve_url(soup, href)
            if image_url not in animal["images"]:
                animal["images"].append(image_url)

    # Also check img tags for images not wrapped in links
    if not animal["images"]:
        for img in soup.find_all("img", src=True):
            src = img["src"]
            if "imagenes/catalogos/" in src:
                image_url = resolve_url(soup, src.replace("/low/", "/high/"))
                if image_url not in animal["images"]:
                    animal["images"].append(image_url)

    # Videos: YouTube links
    for link in soup.find_all("a", href=True):
        href = link["href"]
        if "youtube.com" in href or "youtu.be" in href:
            if href not in animal["videos"]:
                animal["videos"].append(href)

    # Also check for iframe embeds
    for iframe in soup.find_all("iframe", src=True):
        src = iframe["src"]
        if "youtube.com" in src or "youtu.be" in src:
            if src not in animal["videos"]:
                animal["videos"].append(src)

    return animal


def main():
    os.makedirs(OUTPUT_DIR, exist_ok=True)

    # Step 1: Collect all animal URLs
    print("=" * 60)
    print("Step 1: Collecting animal URLs from listing pages")
    print("=" * 60)
    dog_urls, cat_urls = get_all_animal_urls()
    print(f"\nFound {len(dog_urls)} dogs and {len(cat_urls)} cats")

    # Step 2: Scrape individual animal pages
    print("\n" + "=" * 60)
    print("Step 2: Scraping individual animal pages")
    print("=" * 60)
    animals = []

    for i, url in enumerate(dog_urls, 1):
        print(f"\n[Dog {i}/{len(dog_urls)}]")
        try:
            animal = parse_animal_detail(url, "dog")
            animals.append(animal)
            print(f"  Name: {animal['name']}, Images: {len(animal['images'])}, Videos: {len(animal['videos'])}")
        except Exception as e:
            print(f"  ERROR scraping {url}: {e}")

    for i, url in enumerate(cat_urls, 1):
        print(f"\n[Cat {i}/{len(cat_urls)}]")
        try:
            animal = parse_animal_detail(url, "cat")
            animals.append(animal)
            print(f"  Name: {animal['name']}, Images: {len(animal['images'])}, Videos: {len(animal['videos'])}")
        except Exception as e:
            print(f"  ERROR scraping {url}: {e}")

    # Step 3: Save JSON with image URLs
    print("\n" + "=" * 60)
    print("Step 3: Saving data to JSON")
    print("=" * 60)

    with open(DATA_FILE, "w", encoding="utf-8") as f:
        json.dump(animals, f, ensure_ascii=False, indent=2)

    print(f"\nSaved {len(animals)} animals to {DATA_FILE}")
    print("\nDone!")


if __name__ == "__main__":
    main()
