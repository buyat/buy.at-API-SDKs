/******************************************************************************

@LICENCE@
 
*****************************************************************************/

using System;
using System.Collections.Generic;
using System.Text;

namespace BuyAtAPI
{

    public class BuyAtAPIClient
    {
        /// <summary>
        /// Enum to define sort fields
        /// </summary>
        public enum ProductField
        {
            /** Relevance */
            relevance,
            /** Product name */
            productName,
            /** Product SKU */
            productSKU,
            /** Brand name */
            brandName,
            /** Online price */
            onlinePrice
        };

        /// <summary>
        /// Enum to define sort order
        /// </summary>
        public enum SortOrder
        {
            /** Ascending */
            asc,
            /** Descending */
            desc
        };

        /// <summary>
        /// Enum to define feed formats
        /// </summary>
        public enum FeedFormat
        {
            /** Gzipped XML */
            XMLGZIP,
            /** XML */
            XML,
            /** Gzipped CSV */
            CSVGZIP,
            /** Comma-separated */
            CSV,
            /** Gzipped pipe-separated */
            PIPEGZIP,
            /** Pipe-separated */
            PIPE,
            /** Gzipped SCSV */
            SCSVGZIP,
            /** Simple CSV (no quotes round fields) */
            SCSV,
            /** Headers only */
            HEAD,
            /** Sample feed file */
            SAMPLE,
        };

        private string apiKey;
        private BuyatAffiliateBinding binding;

        public BuyAtAPIClient(string apiKey)
        {
            this.apiKey = apiKey;
            this.binding = new BuyatAffiliateBinding();
        }

        public ProductResultSet SearchProducts(string query, int page, int perPage,
                    int[] programmeIds, int[] excludedProgrammeIds, int[] excludedProgrammeCategoryIds,
                    int[] feedIds, int level1CategoryId, int level2CategoryId, bool includeAdult,
                    string lid, ProductField sort, SortOrder sortOrder)
        {
            BuyatAffiliateProductSearchParameters searchParams = new BuyatAffiliateProductSearchParameters();
            searchParams.query = query;
            if (page != -1)
            {
                searchParams.page = page.ToString();
            }
            if (perPage != -1)
            {
                searchParams.perpage = perPage.ToString();
            }
            if (programmeIds != null)
            {
                searchParams.programme_ids = Implode(programmeIds, ",");
            }
            if (programmeIds != null)
            {
                searchParams.excluded_programme_ids = Implode(excludedProgrammeIds, ",");
            }
            if (programmeIds != null)
            {
                searchParams.excluded_programme_category_ids = Implode(excludedProgrammeCategoryIds, ",");
            }
            if (programmeIds != null)
            {
                searchParams.feed_ids = Implode(feedIds, ",");
            }
            if (level1CategoryId != -1)
            {
                searchParams.level1_category_id = level1CategoryId.ToString();
            }
            if (level2CategoryId != -1)
            {
                searchParams.level2_category_id = level2CategoryId.ToString();
            }
            searchParams.include_adult = includeAdult;
            searchParams.lid = lid;
            searchParams.sort = (BuyatAffiliateEntitiesSortType)Enum.ToObject(typeof(BuyatAffiliateEntitiesSortType), sort);
            searchParams.sortorder = (BuyatAffiliateEntitiesSortOrder)Enum.ToObject(typeof(BuyatAffiliateEntitiesSortOrder), sortOrder);

            BuyatAffiliateProductSearchResponse searchResponse = binding.buyatAffiliateProductSearch(apiKey, searchParams);
            return new ProductResultSet(this, searchResponse);
        }

        public ProductResultSet SearchProducts(string query, int page, int perPage,
                string lid, ProductField sort, SortOrder sortOrder)
        {
            return SearchProducts(query, page, perPage, null, null, null, null, -1,
                -1, false, lid, sort, sortOrder);
        }

        public ProductResultSet SearchProducts(string query)
        {
            return SearchProducts(query, 1, 10, null, ProductField.relevance, SortOrder.desc);
        }

        public List<Category> ListLevel1Categories()
        {
            BuyatAffiliateCategoryListResponse response = binding.buyatAffiliateCategoryListlevel1(apiKey);
            List<Category> categories = new List<Category>();
            foreach (BuyatAffiliateEntitiesCategory category in response.categories)
            {
                categories.Add(new Category(this, category));
            }
            return categories;
        }

        public List<Category> ListLevel2Categories(int level1CategoryID)
        {
            BuyatAffiliateCategoryListlevel2Parameters parameters = new BuyatAffiliateCategoryListlevel2Parameters();
            parameters.level1_category_id = level1CategoryID.ToString();

            BuyatAffiliateCategoryListResponse response = binding.buyatAffiliateCategoryListlevel2(apiKey, parameters);
            List<Category> categories = new List<Category>();
            foreach (BuyatAffiliateEntitiesCategory category in response.categories)
            {
                categories.Add(new Category(this, category));
            }
            return categories;
        }

        public List<Category> ListLevel2Categories(Category level1Category)
        {
            return this.ListLevel2Categories(level1Category.CategoryID);
        }

        public List<CategoryTree> GetCategoryTree()
        {
            BuyatAffiliateCategoryTreeResponse response = binding.buyatAffiliateCategoryTree(apiKey);
            List<CategoryTree> categories = new List<CategoryTree>();
            foreach (BuyatAffiliateEntitiesCategoryTree category in response.categories)
            {
                categories.Add(new CategoryTree(category));
            }
            return categories;
        }

        public List<Programme> ListProgrammes()
        {
            BuyatAffiliateProgrammeListResponse response = binding.buyatAffiliateProgrammeList(apiKey);
            List<Programme> programmes = new List<Programme>();
            foreach (BuyatAffiliateEntitiesProgramme programme in response.programmes)
            {
                programmes.Add(new Programme(programme));
            }
            return programmes;
        }

        public List<Feed> ListFeeds()
        {
            BuyatAffiliateFeedListResponse response = binding.buyatAffiliateFeedList(apiKey);
            List<Feed> feeds = new List<Feed>();
            foreach (BuyatAffiliateEntitiesFeed feed in response.feeds)
            {
                feeds.Add(new Feed(this, feed));
            }
            return feeds;
        }

        public string GetFeedURL(int feedID, FeedFormat format, int start, int perPage, int level1CategoryID,
                                 int level2CategoryID, int programmeCategoryID, string[] programmeCategoryLevels,
                                 string lid, bool useHTTPS, bool reverseMapXML, bool bestseller)
        {
            BuyatAffiliateFeedGeturlParameters parameters = new BuyatAffiliateFeedGeturlParameters();
            parameters.feed_id = feedID.ToString();
            parameters.format = (BuyatAffiliateEntitiesFeedFormat)Enum.ToObject(typeof(BuyatAffiliateEntitiesFeedFormat), format);
            if (start != -1)
            {
                parameters.start = start.ToString();
            }
            if (perPage != -1)
            {
                parameters.perpage = perPage.ToString();
            }
            if (level1CategoryID != -1)
            {
                parameters.level1_category_id = level1CategoryID.ToString();
            }
            if (level2CategoryID != -1)
            {
                parameters.level2_category_id = level2CategoryID.ToString();
            }
            if (programmeCategoryID != -1)
            {
                parameters.programme_category_id = programmeCategoryID.ToString();
            }
            if (programmeCategoryLevels != null)
            {
                for (int i = 0; i < programmeCategoryLevels.Length; i++)
                {
                    if (programmeCategoryLevels[i] != null && programmeCategoryLevels[i] != "")
                    {
                        parameters.GetType().GetProperty("level" + (i + 1)).SetValue(parameters, programmeCategoryLevels[i], null);
                    }
                }
            }
            parameters.lid = lid;
            parameters.use_https = useHTTPS;
            parameters.reverse_map_xml = reverseMapXML;
            parameters.bestseller = bestseller;

            BuyatAffiliateFeedGeturlResponse response = binding.buyatAffiliateFeedGeturl(apiKey, parameters);
            return response.url;
        }

        public string CreateDeepLink(string url)
        {
            BuyatAffiliateLinkengineCreateParameters parameters = new BuyatAffiliateLinkengineCreateParameters();
            parameters.url = url;

            BuyatAffiliateLinkengineCreateResponse response = binding.buyatAffiliateLinkengineCreate(apiKey, parameters);
            return response.deep_link;
        }

        public Product GetProductInfo(int productID)
        {
            BuyatAffiliateProductInfoParameters parameters = new BuyatAffiliateProductInfoParameters();
            parameters.product_id = productID.ToString();
            BuyatAffiliateEntitiesProductDetails response = binding.buyatAffiliateProductInfo(apiKey, parameters).product;
            return new Product(this, response);
        }

        public Programme GetProgrammeInfo(int programmeID)
        {
            BuyatAffiliateProgrammeInfoParameters parameters = new BuyatAffiliateProgrammeInfoParameters();
            parameters.programme_id = programmeID.ToString();

            BuyatAffiliateEntitiesProgramme response = binding.buyatAffiliateProgrammeInfo(apiKey, parameters);
            return new Programme(response);
        }

        public Feed GetFeedInfo(int feedID)
        {
            BuyatAffiliateFeedInfoParameters parameters = new BuyatAffiliateFeedInfoParameters();
            parameters.feed_id = feedID.ToString();

            BuyatAffiliateEntitiesFeed response = binding.buyatAffiliateFeedInfo(apiKey, parameters);
            return new Feed(this, response);
        }

        private static string Implode(int[] intArray, string separator)
        {
            return string.Join(separator, Array.ConvertAll<int, string>(intArray, delegate(int x) { return x.ToString(); }));
        }
    }
}
