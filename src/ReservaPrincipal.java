import base.exception.AcomodacaoException;
import base.exception.ClienteException;
import base.exception.ReservaException;
import core.controller.ReservaController;
import core.dao.ReservaDAO;
import core.service.ReservaService;
import core.test.ReservaTest;

public class ReservaPrincipal {

    public static void main(String[] args) throws AcomodacaoException, ClienteException, ReservaException {
        // Inicialização de objetos
        ReservaDAO reservaDAO = new ReservaDAO();
        ReservaService reservaService = new ReservaService(reservaDAO);
        ReservaTest reservaTest = new ReservaTest(reservaService);
        ReservaController reservaController = new ReservaController(reservaTest);
        reservaController.iniciar();
    }
}