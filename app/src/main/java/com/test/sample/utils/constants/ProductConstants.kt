package com.farmdrop.customer.utils.constants

import android.support.annotation.IntDef
import android.support.annotation.StringDef

const val PESTICIDES_FREE = "Pesticide-Free"
const val SUITABLE_FOR_VEGANS = "Local"

@StringDef(PESTICIDES_FREE, SUITABLE_FOR_VEGANS)
@Retention(AnnotationRetention.SOURCE)
annotation class ProductProperties

const val INGREDIENTS = 0
const val COOKING_INSTRUCTIONS = 1
const val STORAGE_INFO = 2
const val NUTRITIONAL_INFO = 3

@IntDef(INGREDIENTS, COOKING_INSTRUCTIONS, STORAGE_INFO, NUTRITIONAL_INFO)
@Retention(AnnotationRetention.SOURCE)
annotation class ProductMoreInfoTypes

const val CALORIES = "calories"
const val CARBOHYDRATES = "carbohydrates"
const val CARBOHYDRATES_POLYOLS = "carbohydratesPolyols"
const val CARBOHYDRATES_STARCH = "carbohydratesStarch"
const val CARBOHYDRATES_SUGAR = "carbohydratesSugar"
const val ENERGY_KJ = "energyKj"
const val FAT = "fat"
const val FAT_MONOUNSATURATES = "fatMonounsaturates"
const val FAT_POLYUNSATURATES = "fatPolyunsaturates"
const val FAT_SATURATES = "fatSaturates"
const val FIBRE = "fibre"
const val PROTEIN = "protein"
const val SALT = "salt"

@StringDef(CALORIES, CARBOHYDRATES, CARBOHYDRATES_POLYOLS, CARBOHYDRATES_STARCH, CARBOHYDRATES_SUGAR, ENERGY_KJ, FAT, FAT_MONOUNSATURATES, FAT_POLYUNSATURATES, FAT_SATURATES, FIBRE, PROTEIN, SALT)
@Retention(AnnotationRetention.SOURCE)
annotation class CoreNutritionType

const val BIOTIN = "biotin"
const val CALCIUM = "calcium"
const val CHLORIDE = "chloride"
const val CHROMIUM = "chromium"
const val COPPER = "copper"
const val FLUORIDE = "fluoride"
const val FOLIC_ACID = "folicAcid"
const val IODINE = "iodine"
const val IRON = "iron"
const val MAGNESIUM = "magnesium"
const val MANGANESE = "manganese"
const val MOLYBDENUM = "molybdenum"
const val NIACIN = "niacin"
const val PHOSPHROUS = "phosphrous"
const val POTASSIUM = "potassium"
const val RIBOFLAVIN = "riboflavin"
const val SELENIUM = "selenium"
const val THIAMIN = "thiamin"
const val ZINC = "zinc"

@StringDef(BIOTIN, CALCIUM, CHLORIDE, CHROMIUM, COPPER, FLUORIDE, FOLIC_ACID, IODINE, IRON, MAGNESIUM, MANGANESE, MOLYBDENUM, MOLYBDENUM, NIACIN, PHOSPHROUS, POTASSIUM, RIBOFLAVIN, SELENIUM, THIAMIN, ZINC)
@Retention(AnnotationRetention.SOURCE)
annotation class MineralType

const val VITAMIN_A = "vitaminA"
const val VITAMIN_B12 = "vitaminB12"
const val VITAMIN_B6 = "vitaminB6"
const val VITAMIN_C = "vitaminC"
const val VITAMIN_D = "vitaminD"
const val VITAMIN_E = "vitaminE"
const val VITAMIN_K = "vitaminK"

@StringDef(VITAMIN_A, VITAMIN_B12, VITAMIN_B6, VITAMIN_C, VITAMIN_D, VITAMIN_E, VITAMIN_K)
@Retention(AnnotationRetention.SOURCE)
annotation class VitaminType

const val CORE: String = "core"
const val MINERALS: String = "minerals"
const val VITAMINS: String = "vitamins"

@StringDef(CORE, MINERALS, VITAMINS)
@Retention(AnnotationRetention.SOURCE)
annotation class NutritionType

const val NUTRITION_CONTENT_TYPE_HEADER = 0
const val NUTRITION_CONTENT_TYPE_CATEGORY = 1
const val NUTRITION_CONTENT_TYPE_PRIMARY_NUTRIENT = 2
const val NUTRITION_CONTENT_TYPE_SECONDARY_NUTRIENT = 3

@IntDef(NUTRITION_CONTENT_TYPE_HEADER, NUTRITION_CONTENT_TYPE_CATEGORY, NUTRITION_CONTENT_TYPE_PRIMARY_NUTRIENT, NUTRITION_CONTENT_TYPE_SECONDARY_NUTRIENT)
@Retention(AnnotationRetention.SOURCE)
annotation class NutritionContentType