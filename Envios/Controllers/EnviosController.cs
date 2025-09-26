using Envios.Repositories;
using Envios.services;
using Microsoft.AspNetCore.Mvc;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace Envios.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class EnviosController : ControllerBase
    {
        private readonly EnvioService _service;
        public EnviosController(EnvioService service)
        {
            _service = service;
        }

        // GET api/<EnviosController>/5
        [HttpGet("{direccion}/{estado}")]
        public IActionResult Get(string direccion, string estado)
        {
            try
            {
                return Ok(_service.ObtenerEnvios(direccion, estado));
            }
            catch (Exception ex)
            {
                return StatusCode(500, $"Error interno del servidor");
            }
        }
        // DELETE api/<EnviosController>/5
        [HttpDelete("{id}")]
        public IActionResult Delete(int id)
        {
            try
            {
                _service.CancelarEnvio(id);
                return Ok("Envio Cancelado con exito");
            }
            catch (Exception)
            {
                return BadRequest("Id invalido");
                
            }
        }
    } 
}
