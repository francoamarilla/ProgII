namespace Envios.Models.DTOS
{
    public class EnvioDTO
    {
        public int Id { get; set; }
        public string Direccion { get; set; }
        public string Estado { get; set; }
        public List<DetalleEnvioDTO> Detalles { get; set; }
    }
}
