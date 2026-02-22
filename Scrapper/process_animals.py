import json
import os
import re
import unicodedata

INPUT_FILE = os.path.join(os.path.dirname(__file__), "output", "animals.json")
OUTPUT_FILE = os.path.join(os.path.dirname(__file__), "output", "studied_animals.json")

# ── Breed normalization ──────────────────────────────────────────────────────

BREED_MAP = {
    # Mastín variants
    "mastin": "Mastín",
    "mastín": "Mastín",
    "mastin x": "Mastín Mix",
    "mastín x": "Mastín Mix",
    # Pastor Alemán variants
    "pastor aleman": "Pastor Alemán",
    "pastor alemán": "Pastor Alemán",
    "pastor aleman x": "Pastor Alemán Mix",
    "pastor alemán x": "Pastor Alemán Mix",
    # Pastor Belga
    "pastor belga": "Pastor Belga",
    "pastor belga x": "Pastor Belga Mix",
    # Malinois
    "malinois": "Malinois",
    "malinois x": "Malinois Mix",
    # Podenco
    "podenco": "Podenco",
    "podenco x": "Podenco Mix",
    # Bodeguero
    "bodeguero": "Bodeguero",
    # Labrador
    "labrador x": "Labrador Mix",
    # Mestizo
    "mestizo": "Mestizo",
    # Cat breeds
    "comun europeo": "Común Europeo",
    "comun europea": "Común Europeo",
    # Specific breeds
    "alano x": "Alano Mix",
    "american stanford terrier x": "American Staffordshire Terrier Mix",
    "amorosos cabezones": "Amorosos Cabezones",
    "cocker spaniel x": "Cocker Spaniel Mix",
    "epagneul breton x": "Épagneul Bretón Mix",
    "gran danes x": "Gran Danés Mix",
    "perro de agua": "Perro de Agua",
    "setter inglés": "Setter Inglés",
    "setter ingles": "Setter Inglés",
}


def normalize_breed(breed):
    if not breed:
        return "Desconocido"
    key = breed.strip().lower()
    return BREED_MAP.get(key, breed.strip())


# ── Size normalization ───────────────────────────────────────────────────────

SIZE_MAP = {
    "pequeño": "small",
    "mediano": "medium",
    "grande": "large",
    "muy grande": "extra_large",
}


def normalize_size(size):
    if not size:
        return "unknown"
    return SIZE_MAP.get(size.strip().lower(), "unknown")


# ── Age normalization (to months) ────────────────────────────────────────────

def normalize_age_months(age):
    if not age:
        return None
    age = age.strip().lower()
    if "menos de 1" in age:
        return 6
    m = re.match(r"(\d+)\s*año", age)
    if m:
        return int(m.group(1)) * 12
    return None


# ── Behavioral scoring ───────────────────────────────────────────────────────
# We score each trait 0-10 by combining:
#   1) Breed-based defaults (known temperament)
#   2) Description keyword analysis (Spanish text)

# Breed defaults: (friendly, good_with_animals, leash, reactive, special_needs,
#                  energy, good_with_humans, shy, activity, trainability, daily_activity)
DEFAULT_SCORES = {
    "friendly": 5, "good_with_animals": 5, "leash_trained": 5,
    "reactive": 3, "special_needs": 0, "energy": 5,
    "good_with_humans": 5, "shy": 3, "activity": 5,
    "trainability": 5, "daily_activity_requirement": 5,
}

BREED_DEFAULTS = {
    "Mastín": {"friendly": 6, "good_with_animals": 4, "leash_trained": 4,
               "reactive": 4, "energy": 4, "good_with_humans": 6, "shy": 4,
               "activity": 4, "trainability": 4, "daily_activity_requirement": 4},
    "Mastín Mix": {"friendly": 6, "good_with_animals": 4, "leash_trained": 4,
                   "reactive": 4, "energy": 4, "good_with_humans": 6, "shy": 4,
                   "activity": 4, "trainability": 4, "daily_activity_requirement": 4},
    "Pastor Alemán": {"friendly": 6, "good_with_animals": 4, "leash_trained": 6,
                      "reactive": 5, "energy": 7, "good_with_humans": 6, "shy": 3,
                      "activity": 7, "trainability": 8, "daily_activity_requirement": 7},
    "Pastor Alemán Mix": {"friendly": 6, "good_with_animals": 4, "leash_trained": 5,
                          "reactive": 5, "energy": 7, "good_with_humans": 6, "shy": 3,
                          "activity": 7, "trainability": 7, "daily_activity_requirement": 7},
    "Pastor Belga": {"friendly": 5, "good_with_animals": 4, "leash_trained": 6,
                     "reactive": 5, "energy": 8, "good_with_humans": 6, "shy": 3,
                     "activity": 8, "trainability": 8, "daily_activity_requirement": 8},
    "Pastor Belga Mix": {"friendly": 5, "good_with_animals": 4, "leash_trained": 5,
                         "reactive": 5, "energy": 7, "good_with_humans": 6, "shy": 3,
                         "activity": 7, "trainability": 7, "daily_activity_requirement": 7},
    "Malinois": {"friendly": 5, "good_with_animals": 4, "leash_trained": 6,
                 "reactive": 6, "energy": 9, "good_with_humans": 6, "shy": 2,
                 "activity": 9, "trainability": 9, "daily_activity_requirement": 9},
    "Malinois Mix": {"friendly": 5, "good_with_animals": 4, "leash_trained": 5,
                     "reactive": 5, "energy": 8, "good_with_humans": 6, "shy": 2,
                     "activity": 8, "trainability": 8, "daily_activity_requirement": 8},
    "Podenco": {"friendly": 6, "good_with_animals": 5, "leash_trained": 4,
                "reactive": 5, "energy": 8, "good_with_humans": 6, "shy": 4,
                "activity": 8, "trainability": 5, "daily_activity_requirement": 8},
    "Podenco Mix": {"friendly": 6, "good_with_animals": 5, "leash_trained": 4,
                    "reactive": 5, "energy": 7, "good_with_humans": 6, "shy": 4,
                    "activity": 7, "trainability": 5, "daily_activity_requirement": 7},
    "Bodeguero": {"friendly": 7, "good_with_animals": 6, "leash_trained": 5,
                  "reactive": 3, "energy": 7, "good_with_humans": 8, "shy": 2,
                  "activity": 7, "trainability": 6, "daily_activity_requirement": 6},
    "Labrador Mix": {"friendly": 8, "good_with_animals": 7, "leash_trained": 6,
                     "reactive": 2, "energy": 7, "good_with_humans": 8, "shy": 2,
                     "activity": 7, "trainability": 7, "daily_activity_requirement": 7},
    "Mestizo": {"friendly": 5, "good_with_animals": 5, "leash_trained": 5,
                "reactive": 3, "energy": 5, "good_with_humans": 5, "shy": 3,
                "activity": 5, "trainability": 5, "daily_activity_requirement": 5},
    "Común Europeo": {"friendly": 5, "good_with_animals": 4, "leash_trained": 2,
                      "reactive": 3, "energy": 5, "good_with_humans": 5, "shy": 5,
                      "activity": 5, "trainability": 4, "daily_activity_requirement": 4},
    "Alano Mix": {"friendly": 5, "good_with_animals": 3, "leash_trained": 5,
                  "reactive": 5, "energy": 6, "good_with_humans": 6, "shy": 3,
                  "activity": 6, "trainability": 6, "daily_activity_requirement": 6},
    "American Staffordshire Terrier Mix": {"friendly": 6, "good_with_animals": 3, "leash_trained": 5,
                                           "reactive": 5, "energy": 7, "good_with_humans": 7, "shy": 2,
                                           "activity": 7, "trainability": 6, "daily_activity_requirement": 7},
    "Amorosos Cabezones": {"friendly": 7, "good_with_animals": 5, "leash_trained": 5,
                           "reactive": 3, "energy": 5, "good_with_humans": 7, "shy": 3,
                           "activity": 5, "trainability": 5, "daily_activity_requirement": 5},
    "Cocker Spaniel Mix": {"friendly": 7, "good_with_animals": 6, "leash_trained": 6,
                           "reactive": 3, "energy": 6, "good_with_humans": 7, "shy": 3,
                           "activity": 6, "trainability": 6, "daily_activity_requirement": 6},
    "Épagneul Bretón Mix": {"friendly": 7, "good_with_animals": 6, "leash_trained": 5,
                            "reactive": 3, "energy": 7, "good_with_humans": 7, "shy": 3,
                            "activity": 7, "trainability": 7, "daily_activity_requirement": 7},
    "Gran Danés Mix": {"friendly": 7, "good_with_animals": 5, "leash_trained": 5,
                       "reactive": 3, "energy": 4, "good_with_humans": 7, "shy": 3,
                       "activity": 4, "trainability": 5, "daily_activity_requirement": 5},
    "Perro de Agua": {"friendly": 6, "good_with_animals": 5, "leash_trained": 6,
                      "reactive": 4, "energy": 7, "good_with_humans": 6, "shy": 4,
                      "activity": 7, "trainability": 7, "daily_activity_requirement": 7},
    "Setter Inglés": {"friendly": 7, "good_with_animals": 6, "leash_trained": 5,
                      "reactive": 3, "energy": 7, "good_with_humans": 7, "shy": 3,
                      "activity": 7, "trainability": 6, "daily_activity_requirement": 7},
}


def strip_accents(s):
    return "".join(
        c for c in unicodedata.normalize("NFD", s) if unicodedata.category(c) != "Mn"
    )


def desc_lower(description):
    """Return lowered + accent-stripped version for keyword matching."""
    if not description:
        return ""
    return strip_accents(description.lower())


# Each rule: (keywords_list, trait, delta)
# Positive keywords push a score up, negative ones push it down.
KEYWORD_RULES = [
    # ── friendly / social ────────────────────────────────────────────────
    (["carinoso", "carinosa", "mimoso", "mimosa", "sociable", "simpatico",
      "simpatica", "dulce", "amoroso", "amorosa", "encantador", "encantadora",
      "afectuoso", "afectuosa", "muy bueno", "muy buena", "adorable",
      "se deja acariciar", "le encantan los mimos", "le gusta que le acaricien",
      "busca caricias", "busca el contacto", "caracter muy bueno"], "friendly", +2),
    (["desconfiado", "desconfiada", "arisco", "arisca", "no se deja tocar",
      "no le gusta que le toquen", "distante"], "friendly", -2),

    # ── good with animals ────────────────────────────────────────────────
    (["se lleva bien con otros perros", "se lleva bien con perros",
      "compatible con otros", "convive con perros", "convive con gatos",
      "bueno con otros perros", "buena con otros perros", "juega con otros",
      "le gustan los perros", "se lleva bien con otros animales",
      "con otros perros bien", "sociable con perros", "sociable con otros"], "good_with_animals", +2),
    (["no se lleva bien con perros", "no compatible con", "problemas con otros",
      "no perros", "sin otros perros", "no convive con perros",
      "no le gustan los perros", "selectivo con perros", "selectiva con perros",
      "selectivo con otros", "selectiva con otros"], "good_with_animals", -3),

    # ── leash trained ────────────────────────────────────────────────────
    (["pasea bien con correa", "pasea genial", "camina bien con correa",
      "sabe pasear", "pasea muy bien", "le encanta pasear",
      "le gusta pasear", "le gusta salir a pasear", "sale a pasear",
      "salir a pasear con correa", "pasea perfectamente"], "leash_trained", +2),
    (["tira de la correa", "tira mucho", "no sabe pasear",
      "le cuesta pasear", "necesita aprender a pasear"], "leash_trained", -2),

    # ── reactive ─────────────────────────────────────────────────────────
    (["reactivo", "reactiva", "reactividad", "ladra a otros perros",
      "ladra a otros", "se activa", "reacciona", "se pone nervioso con otros",
      "se pone nerviosa con otros", "tirón", "tirones"], "reactive", +3),
    (["nada reactivo", "nada reactiva", "tranquilo con otros",
      "tranquila con otros", "no reacciona", "no ladra"], "reactive", -2),

    # ── special needs ────────────────────────────────────────────────────
    (["amputacion", "tres patas", "ciego", "ciega", "sordo", "sorda",
      "leishmania", "leishmaniosis", "medicacion", "tratamiento medico",
      "enfermedad cronica", "epilepsia", "diabetes", "tumor", "operacion",
      "necesidades especiales", "atencion veterinaria", "displasia",
      "problema de salud", "problema cardiaco", "corazon", "atropellado",
      "atropellada", "fractura", "luxacion", "discapacidad"], "special_needs", +3),

    # ── energy ───────────────────────────────────────────────────────────
    (["muy activo", "muy activa", "energico", "energica", "inagotable",
      "necesita actividad", "necesita ejercicio", "necesita mucho ejercicio",
      "jugueton", "juguetona", "no para", "correr", "muy vital"], "energy", +2),
    (["tranquilo", "tranquila", "calmado", "calmada", "relajado", "relajada",
      "casero", "casera", "poco activo", "poco activa", "mayor",
      "sosegado", "sosegada"], "energy", -2),

    # ── good with humans ─────────────────────────────────────────────────
    (["le encantan las personas", "bueno con personas", "buena con personas",
      "le gustan las personas", "adora a las personas", "ninos", "ninas",
      "familia", "bueno con la gente", "buena con la gente",
      "se lleva bien con personas", "le gusta la gente", "todo el mundo",
      "adora a la gente", "con personas genial", "con humanos genial"], "good_with_humans", +2),
    (["desconfia de personas", "miedo a las personas", "miedo a la gente",
      "no le gustan las personas", "desconfiado con personas",
      "desconfiada con personas"], "good_with_humans", -2),

    # ── shy ──────────────────────────────────────────────────────────────
    (["timido", "timida", "miedoso", "miedosa", "asustadizo", "asustadiza",
      "desconfiado", "desconfiada", "inseguro", "insegura", "le cuesta",
      "vergonzoso", "vergonzosa", "miedo", "miedos", "lleno de miedos",
      "le teme", "le da miedo", "no confia"], "shy", +3),
    (["seguro de si", "segura de si", "confiado", "confiada",
      "valiente", "atrevido", "atrevida", "no tiene miedo",
      "sin miedo", "extrovertido", "extrovertida", "lanzado", "lanzada"], "shy", -2),

    # ── activity ─────────────────────────────────────────────────────────
    (["activo", "activa", "jugueton", "juguetona", "corretear",
      "jugar", "le encanta jugar", "senderismo", "caminatas",
      "explorar", "excursiones", "deporte"], "activity", +2),
    (["tranquilo", "tranquila", "calmado", "calmada", "mayor",
      "paseos cortos", "poca actividad", "relajado", "relajada"], "activity", -2),

    # ── trainability ─────────────────────────────────────────────────────
    (["obediente", "inteligente", "lista", "listo", "aprende rapido",
      "aprende muy rapido", "facil de adiestrar", "facil de educar",
      "comandos", "ordenes", "sabe sentarse", "sabe dar la pata",
      "muy lista", "muy listo", "adiestramiento", "aprender"], "trainability", +2),
    (["cabezota", "testarudo", "testaruda", "tozudo", "tozuda",
      "le cuesta aprender", "dificil de educar"], "trainability", -2),

    # ── daily activity requirement ───────────────────────────────────────
    (["necesita actividad", "necesita ejercicio", "necesita mucho ejercicio",
      "senderismo", "caminatas largas", "muy activo", "muy activa",
      "necesita correr", "necesita quemar energia",
      "requiere actividad", "persona activa"], "daily_activity_requirement", +2),
    (["paseos cortos", "poca actividad", "tranquilo", "tranquila",
      "no necesita mucho ejercicio", "mayor", "casero", "casera"], "daily_activity_requirement", -2),
]

# Age adjustments: puppies are generally more energetic, friendly, trainable
# Senior dogs tend to be calmer, may have more health needs
AGE_ADJUSTMENTS = {
    # (min_months, max_months): {trait: delta}
    (0, 12): {"energy": +2, "activity": +2, "trainability": +1,
              "friendly": +1, "daily_activity_requirement": +1, "shy": -1},
    (12, 36): {"energy": +1, "activity": +1, "trainability": +1},
    (96, 144): {"energy": -1, "activity": -1, "special_needs": +1,
                "daily_activity_requirement": -1},
    (144, 999): {"energy": -2, "activity": -2, "special_needs": +2,
                 "daily_activity_requirement": -2},
}

# Size adjustments for daily activity
SIZE_ACTIVITY = {
    "small": {"daily_activity_requirement": -1, "energy": -1},
    "extra_large": {"daily_activity_requirement": +1},
}


def clamp(val, lo=0, hi=10):
    return max(lo, min(hi, val))


def score_animal(animal):
    breed = animal["breed"]
    description = animal.get("description") or ""
    desc = desc_lower(description)
    age_months = animal.get("age_months")
    size = animal.get("size")

    # Start with breed defaults or global defaults
    scores = dict(DEFAULT_SCORES)
    if breed in BREED_DEFAULTS:
        scores.update(BREED_DEFAULTS[breed])

    # Apply keyword rules from description
    for keywords, trait, delta in KEYWORD_RULES:
        for kw in keywords:
            if kw in desc:
                scores[trait] = scores[trait] + delta
                break  # only apply each rule once

    # Apply age adjustments
    if age_months is not None:
        for (lo, hi), adjustments in AGE_ADJUSTMENTS.items():
            if lo <= age_months < hi:
                for trait, delta in adjustments.items():
                    scores[trait] = scores[trait] + delta
                break

    # Apply size adjustments
    if size in SIZE_ACTIVITY:
        for trait, delta in SIZE_ACTIVITY[size].items():
            scores[trait] = scores[trait] + delta

    # Clamp all scores to 0-10
    return {k: clamp(v) for k, v in scores.items()}


# ── Main ─────────────────────────────────────────────────────────────────────

def main():
    with open(INPUT_FILE, "r", encoding="utf-8") as f:
        animals = json.load(f)

    output = []
    for animal in animals:
        breed = normalize_breed(animal.get("breed"))
        size = normalize_size(animal.get("size"))
        age_months = normalize_age_months(animal.get("age"))

        processed = {
            "id": animal["id"],
            "animal_type": animal["animal_type"],
            "name": animal["name"],
            "sex": animal["sex"],
            "breed": breed,
            "size": size,
            "age_months": age_months,
            "description": animal["description"],
            "images": animal["images"],
            "videos": animal["videos"],
            "source_url": animal["source_url"],
        }

        scores = score_animal(processed)
        processed["scores"] = scores
        output.append(processed)

    with open(OUTPUT_FILE, "w", encoding="utf-8") as f:
        json.dump(output, f, ensure_ascii=False, indent=2)

    print(f"Processed {len(output)} animals → {OUTPUT_FILE}")

    # Print summary stats
    breeds_count = {}
    for a in output:
        breeds_count[a["breed"]] = breeds_count.get(a["breed"], 0) + 1
    print("\nBreed distribution:")
    for b, c in sorted(breeds_count.items(), key=lambda x: -x[1]):
        print(f"  {c:>3}x  {b}")


if __name__ == "__main__":
    main()
