/******************************************************************************

@LICENCE@
 
 *****************************************************************************/

using System;
using System.Collections.Generic;
using System.Text;

namespace BuyAtAPI
{

    public class Product
    {

        private BuyAtAPIClient apiClient;
        private BuyatAffiliateEntitiesProductDetails soapProductDetails;
        private Programme programme;
        private Feed feed;

        /// <summary>
        /// Constructor, wrapping SOAP object
        /// </summary>
        public Product(BuyAtAPIClient apiClient, BuyatAffiliateEntitiesProduct soapProduct)
        {
            this.apiClient = apiClient;
            this.soapProductDetails = soapProduct.product;
        }

        /// <summary>
        /// Constructor, wrapping SOAP object
        /// </summary>
        public Product(BuyAtAPIClient apiClient, BuyatAffiliateEntitiesProductDetails soapProductDetails)
        {
            this.apiClient = apiClient;
            this.soapProductDetails = soapProductDetails;
        }

        /// <summary>
        /// The product ID
        /// </summary>
        public int ProductID
        {
            get
            {
                try
                {
                    return Convert.ToInt32(soapProductDetails.product_id);
                }
                catch (FormatException)
                {
                    return -1;
                }
            }
        }

        /// <summary>
        /// The product SKU
        /// </summary>
        public string ProductSKU
        {
            get
            {
                return soapProductDetails.product_sku;
            }
        }

        /// <summary>
        /// The product URL
        /// </summary>
        public string ProductURL
        {
            get
            {
                return soapProductDetails.product_url;
            }
        }

        /// <summary>
        /// The product name
        /// </summary>
        public string ProductName
        {
            get
            {
                return soapProductDetails.product_name;
            }
        }

        /// <summary>
        /// The brand name
        /// </summary>
        public string BrandName
        {
            get
            {
                return soapProductDetails.brand_name;
            }
        }

        /// <summary>
        /// The product description
        /// </summary>
        public string Description
        {
            get
            {
                return soapProductDetails.description;
            }
        }

        /// <summary>
        /// The online price of the product
        /// </summary>
        public float OnlinePrice
        {
            get
            {
                try
                {
                    return Convert.ToSingle(soapProductDetails.online_price);
                }
                catch (FormatException)
                {
                    return -1;
                }
            }
        }

        /// <summary>
        /// The 3-letter currency code for the product's currency
        /// </summary>
        public string Currency
        {
            get
            {
                return soapProductDetails.currency;
            }
        }

        /// <summary>
        /// The HTML-encoded currency symbol for the product's currency
        /// </summary>
        public string CurrencySymbol
        {
            get
            {
                return soapProductDetails.currency_symbol;
            }
        }

        /// <summary>
        /// The image URL
        /// </summary>
        public string ImageURL
        {
            get
            {
                return soapProductDetails.image_url;
            }
        }

        /// <summary>
        /// The ID of the product's programme
        /// </summary>
        public int ProgrammeID
        {
            get
            {
                try
                {
                    return Convert.ToInt32(soapProductDetails.programme_id);
                }
                catch (FormatException)
                {
                    return -1;
                }
            }
        }

        /// <summary>
        /// The name of the product's programme
        /// </summary>
        public string ProgrammeName
        {
            get
            {
                return soapProductDetails.programme_name;
            }
        }

        /// <summary>
        /// The URL of the product's programme
        /// </summary>
        public string getProgrammeURL()
        {
            return soapProductDetails.programme_url;
        }

        /// <summary>
        /// The product's level 1 category ID
        /// </summary>
        public int Level1CategoryID
        {
            get
            {
                try
                {
                    return Convert.ToInt32(soapProductDetails.level1_category_id);
                }
                catch (FormatException)
                {
                    return -1;
                }
            }
        }

        /// <summary>
        /// The product's level 1 category name
        /// </summary>
        public string Level1CategoryName
        {
            get
            {
                return soapProductDetails.level1_category_name;
            }
        }

        /// <summary>
        /// The product's level 2 category ID
        /// </summary>
        public int Level2CategoryID
        {
            get
            {
                try
                {
                    return Convert.ToInt32(soapProductDetails.level2_category_id);
                }
                catch (FormatException)
                {
                    return -1;
                }
            }
        }

        /// <summary>
        /// The product's level 2 category name
        /// </summary>
        public string Level2CategoryName
        {
            get
            {
                return soapProductDetails.level1_category_name;
            }
        }

        /// <summary>
        /// The product's feed ID
        /// </summary>
        public int FeedID
        {
            get
            {
                try
                {
                    return Convert.ToInt32(soapProductDetails.feed_id);
                }
                catch (FormatException)
                {
                    return -1;
                }
            }
        }

        /// <summary>
        /// The product's feed name
        /// </summary>
        public string FeedName
        {
            get
            {
                return soapProductDetails.feed_name;
            }
        }

        /// <summary>
        /// The programme associated with this product
        /// </summary>
        public Programme Programme
        {
            get
            {
                if (this.programme == null)
                {
                    this.programme = this.apiClient.GetProgrammeInfo(this.ProgrammeID);
                }
                return this.programme;
            }
        }

        /// <summary>
        /// The programme associated with this product
        /// </summary>
        public Feed Feed
        {
            get
            {
                if (this.feed == null)
                {
                    this.feed = this.apiClient.GetFeedInfo(this.FeedID);
                }
                return this.feed;
            }
        }
    }
}
