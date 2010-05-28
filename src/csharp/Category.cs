/******************************************************************************

@LICENCE@
 
 *****************************************************************************/

using System;
using System.Collections.Generic;
using System.Text;

namespace BuyAtAPI
{

    public class Category
    {

        private BuyAtAPIClient apiClient;
        private BuyatAffiliateEntitiesCategory soapCategory;
        private List<Category> subcategories;

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
        public Category(BuyAtAPIClient apiClient, BuyatAffiliateEntitiesCategory soapCategory)
        {
            this.apiClient = apiClient;
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
        /// The parent category ID, if this is a level 2 category
        /// </summary>
        public int ParentCategoryID
        {
            get
            {
                try
                {
                    return Convert.ToInt32(soapCategory.category.parent_category_id);
                }
                catch (FormatException)
                {
                    return -1;
                }
            }
        }

        /// <summary>
        /// The parent category name, if this is a level 2 category
        /// </summary>
        public string ParentCategoryName
        {
            get
            {
                return soapCategory.category.parent_category_name;
            }
        }

        /// <summary>
        /// The subcategories of this category, if it is a level 1 category
        /// </summary>
        public List<Category> Subcategories
        {
            get
            {
                if (this.subcategories == null && this.Level == LevelType.Level1)
                {
                    this.subcategories = this.apiClient.ListLevel2Categories(this.CategoryID);
                }
                return this.subcategories;
            }
        }

    }
}
