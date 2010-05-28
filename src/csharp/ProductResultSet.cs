/******************************************************************************

@LICENCE@
 
 *****************************************************************************/

using System;
using System.Collections.Generic;
using System.Text;

namespace BuyAtAPI
{
    public class ProductResultSet
    {

        private BuyAtAPIClient apiClient;
        private BuyatAffiliateProductSearchResponse soapResponse;

        private List<Product> products;

        /// <summary>
        /// Constructor, wrapping SOAP object
        /// </summary>
        public ProductResultSet(BuyAtAPIClient apiClient, BuyatAffiliateProductSearchResponse soapResponse)
        {
            this.apiClient = apiClient;
            this.soapResponse = soapResponse;
        }

        /// <summary>
        /// Total number of results
        /// </summary>
        public int TotalResults
        {
            get
            {
                try
                {
                    return Convert.ToInt32(soapResponse.total_results);
                }
                catch (FormatException)
                {
                    return -1;
                }
            }
        }

        /// <summary>
        /// Number of results in current result set
        /// </summary>
        public int CurrentResults
        {
            get
            {
                try
                {
                    return Convert.ToInt32(soapResponse.current_results);
                }
                catch (FormatException)
                {
                    return -1;
                }
            }
        }

        /// <summary>
        /// Starting number of current result set
        /// </summary>
        public int Start
        {
            get
            {
                try
                {
                    return Convert.ToInt32(soapResponse.start);
                }
                catch (FormatException)
                {
                    return -1;
                }
            }
        }

        /// <summary>
        /// Size of current result set
        /// </summary>
        public int Limit
        {
            get
            {
                try
                {
                    return Convert.ToInt32(soapResponse.limit);
                }
                catch (FormatException)
                {
                    return -1;
                }
            }
        }

        /// <summary>
        /// Query string used to retrieve results
        /// </summary>
        public string Query
        {
            get
            {
                return soapResponse.query;
            }
        }

        /// <summary>
        /// List of products found
        /// </summary>
        public List<Product> Products
        {
            get
            {
                if (this.products == null)
                {
                    this.products = new List<Product>();

                    foreach (BuyatAffiliateEntitiesProduct soapProduct in soapResponse.products)
                    {
                        this.products.Add(new Product(this.apiClient, soapProduct));
                    }
                }
                return this.products;
            }
        }
    }
}
