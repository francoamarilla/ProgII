using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Facturacion.DOMAIN
{
    public class DetailBudget
    {
        public int IdDetailBudget { get; set; }
        public Product Product { get; set; }
        public int Count { get; set; }
        public float Price { get; set; }


        public float SubTotal()
        {
            return Count * Price;
        }
    }
}
