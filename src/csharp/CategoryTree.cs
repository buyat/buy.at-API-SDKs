/******************************************************************************

@LICENCE@
 
 *****************************************************************************/

using System;
using System.Collections.Generic;
using System.Text;

namespace BuyAtAPI
{

    public class CategoryTree
    {

        private BuyatAffiliateEntitiesCategoryTree soapCategory;

        private List<CategoryTree> subCategories;

        /// <summary>
        /// Enum to define available levels
        /// </summary>
        public enum LevelType
        {
            /** Level 1 */
            Level1,
            /** Level 2 */
            Level2
        };

        /// <summary>
        /// Constructor, wrapping SOAP object
        /// </summary>
        public CategoryTree(BuyatAffiliateEntitiesCategoryTree soapCategory)
        {
            this.soapCategory = soapCategory;
        }

        /// <summary>
        /// The category ID
        /// </summary>
        public int CategoryID
        {
            get
            {
                try
                {
                    return Convert.ToInt32(soapCategory.category.category_id);
                }
                catch (FormatException)
                {
                    return -1;
                }
            }
        }

        /// <summary>
        /// The category's level
        /// </summary>
        public LevelType Level
        {
            get
            {
                if (soapCategory.category.level == "1")
                {
                    return LevelType.Level1;
                }
                else
                {
                    return LevelType.Level2;
                }
            }
        }

        /// <summary>
        /// The category name
        /// </summary>
        public string CategoryName
        {
            get
            {
                return soapCategory.category.category_name;
            }
        }

        /// <summary>
        /// The categry's subcategories, if this is a level1 category
        /// </summary>
        public List<CategoryTree> Subcategories
        {
            get
            {
                if (this.subCategories == null && this.Level == LevelType.Level1)
                {
                    this.subCategories = new List<CategoryTree>();

                    foreach (BuyatAffiliateEntitiesCategoryTree soapSubCat in soapCategory.category.subcategories)
                    {
                        this.subCategories.Add(new CategoryTree(soapSubCat));
                    }
                }
                return this.subCategories;
            }
        }
    }
}
