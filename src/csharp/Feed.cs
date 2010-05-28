/******************************************************************************

@LICENCE@
 
 *****************************************************************************/

using System;
using System.Collections.Generic;
using System.Text;

namespace BuyAtAPI
{

    public class Feed
    {

        private BuyAtAPIClient apiClient;
        private BuyatAffiliateEntitiesFeed soapFeed;
        private Programme programme;

        /// <summary>
        /// Constructor, wrapping SOAP object
        /// </summary>
        public Feed(BuyAtAPIClient apiClient, BuyatAffiliateEntitiesFeed soapFeed)
        {
            this.apiClient = apiClient;
            this.soapFeed = soapFeed;
        }

        /// <summary>
        /// The Feed ID
        /// </summary>
        public int FeedID
        {
            get
            {
                try
                {
                    return Convert.ToInt32(soapFeed.feed.feed_id);
                }
                catch (FormatException)
                {
                    return -1;
                }
            }
        }

        /// <summary>
        /// The Feed Name
        /// </summary>
        public string FeedName
        {
            get
            {
                return soapFeed.feed.feed_name;
            }
        }

        /// <summary>
        /// The Programme ID
        /// </summary>
        public int ProgrammeID
        {
            get
            {
                try
                {
                    return Convert.ToInt32(soapFeed.feed.programme_id);
                }
                catch (FormatException)
                {
                    return -1;
                }
            }
        }

        /// <summary>
        /// The Programme Name
        /// </summary>
        public string ProgrammeName
        {
            get
            {
                return soapFeed.feed.programme_name;
            }
        }

        /// <summary>
        /// The Programme URL
        /// </summary>
        public string ProgrammeURL
        {
            get
            {
                return soapFeed.feed.programme_url;
            }
        }

        /// <summary>
        /// The number of products in the feed
        /// </summary>
        public int NumberOfProducts
        {
            get
            {
                try
                {
                    return Convert.ToInt32(soapFeed.feed.number_of_products);
                }
                catch (FormatException)
                {
                    return -1;
                }
            }
        }

        /// <summary>
        /// The date the feed was last updated
        /// </summary>
        public DateTime LastUpdated
        {
            get
            {
                return soapFeed.feed.last_updated;
            }
        }

        /// <summary>
        /// The programme associated with this feed
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

    }
}
