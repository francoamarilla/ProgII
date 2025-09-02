using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Facturacion.DOMAIN
{
    public class Budget
    {
        public int IdBudget { get; set; }
        public DateTime Date { get; set; }
        public int IdPago { get; set; }
        public string Client { get; set; }

        public List<DetailBudget> details;
        public Budget(DetailBudget d)
        {
            details = new List<DetailBudget>();
        }
        public void AddDetail(DetailBudget d)
        {
            if (d != null)
            {
                details.Add(d);
            }
        }
        public void Remove(int index)
        { 
        details.RemoveAt(index);
        }
        public float Total()
        {
            float total = 0;
            foreach (DetailBudget d in details)
            {
                total += d.SubTotal();
            }
            return total;
        }

    }
}
