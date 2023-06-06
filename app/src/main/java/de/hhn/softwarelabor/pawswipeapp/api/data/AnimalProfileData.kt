package de.hhn.softwarelabor.pawswipeapp.api.data

/**
 * [AnimalProfileData], holds data for an animal profile
 * @author Felix Kuhbier
 * @since 2023.05.06
 *
 * @property name
 * @property species
 * @property birthday
 * @property illness
 * @property description
 * @property breed
 * @property color
 * @property gender
 * @property profile_id must not be null
 */
data class AnimalProfileData(
    val name: String?,
    val species: String?,
    val birthday: String?,
    val illness: String?,
    val description: String?,
    val breed: String?,
    val color: String?,
    val gender: String?,
    val picture_one: Array<Byte>?,
    val picture_two: Array<Byte>?,
    val picture_three: Array<Byte>?,
    val picture_four: Array<Byte>?,
    val picture_five: Array<Byte>?,
    val profile_id: ProfileData
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AnimalProfileData

        if (name != other.name) return false
        if (species != other.species) return false
        if (birthday != other.birthday) return false
        if (illness != other.illness) return false
        if (description != other.description) return false
        if (breed != other.breed) return false
        if (color != other.color) return false
        if (gender != other.gender) return false
        if (!picture_one.contentEquals(other.picture_one)) return false
        if (!picture_two.contentEquals(other.picture_two)) return false
        if (!picture_three.contentEquals(other.picture_three)) return false
        if (!picture_four.contentEquals(other.picture_four)) return false
        if (!picture_five.contentEquals(other.picture_five)) return false
        if (profile_id != other.profile_id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name?.hashCode() ?: 0
        result = 31 * result + (species?.hashCode() ?: 0)
        result = 31 * result + (birthday?.hashCode() ?: 0)
        result = 31 * result + (illness?.hashCode() ?: 0)
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (breed?.hashCode() ?: 0)
        result = 31 * result + (color?.hashCode() ?: 0)
        result = 31 * result + (gender?.hashCode() ?: 0)
        result = 31 * result + picture_one.contentHashCode()
        result = 31 * result + picture_two.contentHashCode()
        result = 31 * result + picture_three.contentHashCode()
        result = 31 * result + picture_four.contentHashCode()
        result = 31 * result + picture_five.contentHashCode()
        result = 31 * result + profile_id.hashCode()
        return result
    }
}
