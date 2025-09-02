using Facturacion.DOMAIN;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Facturacion.DATA
{
    public interface IBadgetRepository
    {
        bool Save(Budget oBudget);
        List<Budget> GetAll();
        Budget GetById(int id);
    }
}
