package core.test;
import base.enumeration.Funcionalidade;
import base.exception.AcomodacaoException;
import base.exception.ClienteException;
import base.exception.ReservaException;
import core.service.ReservaService;

public class ReservaTest implements Test {

        // Atributos
        private ReservaService reservaService;

        // Construtor
        public ReservaTest(ReservaService reservaService) {
            this.reservaService = reservaService;
        }

        // Métodos de testes

        public String testar(Funcionalidade funcionalidade) throws ReservaException, AcomodacaoException, ClienteException {
            switch (funcionalidade) {
                case LISTAR:
                    return this.listar();
                case CADASTRAR:
                    return this.cadastrar();
                case ALTERAR:
                    return this.alterar();
                case EXCLUIR:
                    return this.excluir();
                default:
                    return null;
            }
        }

        public String listar() throws ReservaException {
            return reservaService.listar();
        }

        public String cadastrar() throws ReservaException, AcomodacaoException, ClienteException {
            // Dados para cadastro
            String dataInicio = "31/09/2024";
            String dataFim = "01/09/2024";
            String idAcomodacao = "1";
            String clienteResponsavel = "1";
            String valorTotal = "";
            String qtdHospedes = "6";

            return reservaService.cadastrar(idAcomodacao, clienteResponsavel, dataInicio, dataFim, valorTotal, qtdHospedes);
        }

        public String alterar() throws ReservaException, AcomodacaoException, ClienteException {
            // Dados para alteração

            String id = "1";
            String dataInicio = "28/10/2025";
            String dataFim = "01/11/2025";
            String idAcomodacao = "1";
            String clienteResponsavel = "1";
            String valorTotal = "310.00";
            String qtdHospedes = "3";

            return reservaService.alterar(id, idAcomodacao, clienteResponsavel, dataInicio, dataFim, valorTotal, qtdHospedes);
        }

        public String excluir() throws ReservaException {
            // Dados para exclusão
            String id = "2";

            return reservaService.excluir(id);
        }

    }

