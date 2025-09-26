using Envios.Models;
using Envios.Models.DTOS;
using Envios.Repositories;

namespace Envios.services
{
    public class EnvioService
    {
        private readonly IEnviosRepository _enviosRepository;
        public EnvioService(IEnviosRepository enviosRepository)
        {
            _enviosRepository = enviosRepository;
        }
        public EnvioDTO ObtenerEnvios(string direccion, string? estado)
        {
            return _enviosRepository.GetEnvios(direccion, estado);
        }
        public void CancelarEnvio(int id)
        {
            _enviosRepository.CancelEnvio(id);
        }
    }
}
