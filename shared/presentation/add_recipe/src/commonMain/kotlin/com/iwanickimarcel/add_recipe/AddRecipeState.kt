package com.iwanickimarcel.add_recipe

import androidx.compose.ui.text.input.TextFieldValue
import com.iwanickimarcel.products.Product
import com.iwanickimarcel.recipes.Recipe

data class AddRecipeState(
    val editId: Long? = null,
    val name: TextFieldValue? = null,
    val nameError: String? = null,
    val photoBytes: ByteArray? = null,
    val ingredients: List<Product> = emptyList(),
    val steps: List<Recipe.Step> = emptyList(),
    val tags: List<Recipe.Tag> = emptyList(),
    var tagsTextFieldValue: TextFieldValue = TextFieldValue(""),
    val addIngredientOpen: Boolean = false,
    val editIngredient: Product? = null,
    val editStep: Recipe.Step? = null,
    val addStepOpen: Boolean = false,
    val success: Boolean = false,
    val finalErrorMessage: String? = null,
) {
    /*
        equals and hashcode have to be overwritten because of photoBytes ByteArray
    */

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AddRecipeState

        if (editId != other.editId) return false
        if (name != other.name) return false
        if (nameError != other.nameError) return false
        if (photoBytes != null) {
            if (other.photoBytes == null) return false
            if (!photoBytes.contentEquals(other.photoBytes)) return false
        } else if (other.photoBytes != null) return false
        if (ingredients != other.ingredients) return false
        if (steps != other.steps) return false
        if (tags != other.tags) return false
        if (tagsTextFieldValue != other.tagsTextFieldValue) return false
        if (addIngredientOpen != other.addIngredientOpen) return false
        if (editIngredient != other.editIngredient) return false
        if (editStep != other.editStep) return false
        if (addStepOpen != other.addStepOpen) return false
        if (success != other.success) return false
        if (finalErrorMessage != other.finalErrorMessage) return false

        return true
    }

    override fun hashCode(): Int {
        var result = editId?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (nameError?.hashCode() ?: 0)
        result = 31 * result + (photoBytes?.contentHashCode() ?: 0)
        result = 31 * result + ingredients.hashCode()
        result = 31 * result + steps.hashCode()
        result = 31 * result + tags.hashCode()
        result = 31 * result + tagsTextFieldValue.hashCode()
        result = 31 * result + addIngredientOpen.hashCode()
        result = 31 * result + (editIngredient?.hashCode() ?: 0)
        result = 31 * result + (editStep?.hashCode() ?: 0)
        result = 31 * result + addStepOpen.hashCode()
        result = 31 * result + success.hashCode()
        result = 31 * result + (finalErrorMessage?.hashCode() ?: 0)
        return result
    }
}