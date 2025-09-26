using Envios.Models;
using Envios.Models.DTOS;
using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;

namespace Envios.Repositories
{
    public class EnviosRepository : IEnviosRepository
    {
        private readonly db_enviosContext _context;
        public EnviosRepository(db_enviosContext context)
        {
            _context = context;
        }
        public void CancelEnvio(int id)
        {
            Envio envio = _context.Envios.Find(id);
            if (envio.Estado != "Cancelado")
            {
                envio.Estado = "Cancelado";
                _context.Envios.Update(envio);
            }
            _context.SaveChanges();
        }

        public EnvioDTO GetEnvios(string direccion, string estado)
        {
            var envio = _context.Envios.Where(e => e.Direccion == direccion && e.Estado == estado).Select(e => 
            new EnvioDTO
            {
                Id = e.Id,
                Direccion = e.Direccion,
                Estado = e.Estado,
                Detalles = e.DetalleEnvios.Select(d => new DetalleEnvioDTO
                {
                    Id = d.Id,
                    Cantidad = d.Cantidad,
                    Comentario = d.Comentario
                }).ToList()
            })
                .FirstOrDefault();
            return envio;
        }
    }
}
