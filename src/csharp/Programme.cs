/******************************************************************************

@LICENCE@
 
 *****************************************************************************/

using System;
using System.Collections.Generic;
using System.Text;

namespace BuyAtAPI
{

    public class Programme
    {

        private BuyatAffiliateEntitiesProgramme soapProgramme;

        /// <summary>
        /// Constructor, wrapping SOAP object
        /// </summary>
        public Programme(BuyatAffiliateEntitiesProgramme soapProgramme)
        {
            this.soapProgramme = soapProgramme;
        }

        /// <summary>
        /// The programme ID
        /// </summary>
        public int ProgrammeID
        {
            get
            {
                try
                {
                    return Convert.ToInt32(soapProgramme.programme.programme_id);
                }
                catch (FormatException)
                {
                    return -1;
                }
            }
        }

        /// <summary>
        /// The programme name
        /// </summary>
        public string ProgrammeName
        {
            get
            {
                return soapProgramme.programme.programme_name;
            }
        }

        /// <summary>
        /// The programme URL
        /// </summary>
        public string ProgrammeURL
        {
            get
            {
                return soapProgramme.programme.programme_url;
            }
        }

        /// <summary>
        /// Whether the programme has feeds associated with it
        /// </summary>
        public bool HasFeed
        {
            get
            {
                return soapProgramme.programme.has_feed;
            }
        }
    }
}
