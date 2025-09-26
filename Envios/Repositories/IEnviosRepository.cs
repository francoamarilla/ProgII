using Envios.Models;
using Envios.Models.DTOS;

namespace Envios.Repositories
{
    public interface IEnviosRepository
    {
        EnvioDTO GetEnvios(string direccion, string? estado);
        void CancelEnvio(int id);
    }
}
