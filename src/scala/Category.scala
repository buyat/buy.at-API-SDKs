/******************************************************************************

@LICENCE@
 
 *****************************************************************************/
 
package buyat.api.entities

/**
 * Represents a buy.at category.
 * @author buy.at
 * @version 1.0
 * @since 1.0
 * @param _categoryId Category ID.
 * @param _categoryName Category name.
 * @param _level Level (1 or 2).
 * @param _parentCategoryId ID of the parent category.
 * @param _parentCategoryName Name of the parent category.
 * @param _subcategories Subcategories of the category.
 */
class Category(_categoryId:Int, _categoryName:String, _level:Int, _parentCategoryId:Int,
                    _parentCategoryName:String, _subcategories:List[Category]) extends APIEntity {

  /**
   * Alternative constructor.
   * @param _categoryId Category ID.
   * @param _categoryName Category name.
   * @param _level Level (1 or 2).
   */
  def this(_categoryId:Int, _categoryName:String, _level:Int) = this(_categoryId, _categoryName, _level, -1, null, Nil)

  /**
   * ID of this category.
   */
  var categoryId:Int = _categoryId

  /**
   * Name of this category.
   */
  var categoryName:String = _categoryName

  /**
   * ID of parent category.
   * -1 if there is no parent category.
   */
  var parentCategoryId:Int = _parentCategoryId

  /**
   * Name of parent category.
   * Null if there is no parent category.
   */
  var parentCategoryName:String = _parentCategoryName

  /**
   * Level of this category.
   */
  var level = _level

  /**
   * List of subcategories.
   */
  var subcategories:List[Category] = _subcategories
}