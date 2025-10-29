package core.service;

import base.enumeration.Funcionalidade;
import base.exception.AcomodacaoException;
import base.exception.ClienteException;
import base.exception.ReservaException;
import base.util.Utilidades;
import core.dao.AcomodacaoDAO;
import core.dao.ClienteDAO;
import core.model.Acomodacao;
import core.model.Cliente;
import core.model.Reserva;
import core.dao.ReservaDAO;

import java.time.LocalDate;
import java.util.ArrayList;

public class ReservaService {

    // Atributos
    private ReservaDAO reservaDAO;

    // Construtor
    public ReservaService(ReservaDAO reservaDAO) {
        this.reservaDAO = reservaDAO;
    }

    // Métodos públicos

    public String listar() throws ReservaException {
        ArrayList<Reserva> reservas = reservaDAO.selecionar();
        String lista = "";
        if (reservas.size() > 0) {
            for (Reserva pessoa : reservas) {
                lista += pessoa + "\n";
            }
        } else {
            lista = "Nenhuma pessoa encontrada.";
        }
        return lista;
    }

    public String cadastrar(

            String idAcomodacao,
            String idClienteResponsavel,
            String dataInicioBR,
            String dataFimBR,
            String valorTotal,
            String qtdHospedes

    ) throws ReservaException, AcomodacaoException, ClienteException {
        String mensagemErro = validarCampos(Funcionalidade.CADASTRAR, null, idAcomodacao, idClienteResponsavel, dataInicioBR, dataFimBR, valorTotal, qtdHospedes);
        if (!mensagemErro.isEmpty()) throw new ReservaException(mensagemErro);

        LocalDate dataInicio = Utilidades.formatarDataLocalDate(dataInicioBR);
        LocalDate dataFim = Utilidades.formatarDataLocalDate(dataFimBR);
        AcomodacaoDAO acomodacaoDAO = new AcomodacaoDAO();
        Long idAcomodacaoNumerico = Long.parseLong(idAcomodacao);
        Acomodacao acomodacao = acomodacaoDAO.selecionarPorId(idAcomodacaoNumerico);
        ClienteDAO clienteDAO = new ClienteDAO();
        Long idClienteNumerico = Long.parseLong(idClienteResponsavel);
        Cliente cliente = clienteDAO.selecionarPorId(idClienteNumerico);
        Integer qtdHospedesInteiro = Integer.parseInt(qtdHospedes);
        Double valorTotalNumerico;
        if (valorTotal != null && !valorTotal.trim().isEmpty()) {
            if (!Utilidades.validarNumero(valorTotal)) {
                throw new ReservaException("Valor total inválido.");
            }
            valorTotalNumerico = Double.parseDouble(valorTotal);
        } else {
            valorTotalNumerico = 0.0; // valor padrão
        }
        Reserva reserva = new Reserva(
                dataInicio,
                dataFim,
                acomodacao,
                cliente,
                qtdHospedesInteiro,
                valorTotalNumerico
        );

        if (reservaDAO.inserir(reserva)) {
            return "Pessoa cadastrada com sucesso!";
        } else {
            throw new ReservaException("Não foi possível cadastrar a pessoa! Por favor, tente novamente.");
        }
    }

    public String alterar(

            String id,
            String idAcomodacao,
            String idClienteResponsavel,
            String dataInicioBR,
            String dataFimBR,
            String valorTotal,
            String qtdHospedes

    ) throws ReservaException, AcomodacaoException, ClienteException {
        String mensagemErro = validarCampos(Funcionalidade.ALTERAR, id, idAcomodacao, idClienteResponsavel, dataInicioBR, dataFimBR, valorTotal, qtdHospedes);
        if (!mensagemErro.isEmpty()) throw new ReservaException(mensagemErro);

        LocalDate dataInicio = Utilidades.formatarDataLocalDate(dataInicioBR);
        LocalDate dataFim = Utilidades.formatarDataLocalDate(dataFimBR);

        AcomodacaoDAO acomodacaoDAO = new AcomodacaoDAO();
        Long idAcomodacaoNumerico = Long.parseLong(idAcomodacao);
        Acomodacao acomodacao = acomodacaoDAO.selecionarPorId(idAcomodacaoNumerico);
        ClienteDAO clienteDAO = new ClienteDAO();
        Long idClienteNumerico = Long.parseLong(idClienteResponsavel);
        Cliente cliente = clienteDAO.selecionarPorId(idClienteNumerico);
        Integer qtdHospedesInteiro = Integer.parseInt(qtdHospedes);
        Double valorTotalNumerico;
        if (valorTotal != null && !valorTotal.trim().isEmpty()) {
            if (!Utilidades.validarNumero(valorTotal)) {
                throw new ReservaException("Valor total inválido.");
            }
            valorTotalNumerico = Double.parseDouble(valorTotal);
        } else {
            valorTotalNumerico = 0.0; // valor padrão
        }
        Long idNumerico = Long.parseLong(id);


        Reserva reserva = new Reserva(
                idNumerico,
                dataInicio,
                dataFim,
                acomodacao,
                cliente,
                qtdHospedesInteiro,
                valorTotalNumerico
        );

        if (reservaDAO.atualizar(reserva)) {
            return "Reserva alterada com sucesso!";
        } else {
            throw new ReservaException("Não foi possível alterar a reserva!! Por favor, tente novamente.");
        }
    }

    public String excluir(String id) throws ReservaException {
        String mensagemErro = validarCampos(Funcionalidade.EXCLUIR, id, null, null, null, null, null, null);
        if (!mensagemErro.isEmpty()) throw new ReservaException(mensagemErro);

        Long idNumerico = Long.parseLong(id);
        if (reservaDAO.deletar(idNumerico)) {
            return "Reserva excluída com sucesso!";
        } else {
            throw new ReservaException("Não foi possível excluir a reserva! Por favor, tente novamente.");
        }
    }

    // Métodos privados

    private String validarCampos(
            Funcionalidade funcionalidade,
            String id,
            String idAcomodacao,
            String idClienteResponsavel,
            String dataInicioBR,
            String dataFimBR,
            String valorTotal,
            String qtdHospedes
    ) throws ReservaException {
        String erros = "";
        // Verificação de id
        if (funcionalidade == Funcionalidade.ALTERAR || funcionalidade == Funcionalidade.EXCLUIR) {
            if (!id.isEmpty()) {
                if (Utilidades.validarNumero(id)) {
                    Long idNumerico = Long.parseLong(id);
                    if (reservaDAO.selecionarPorId(idNumerico) == null) erros += "\n- Id não encontrado.";
                } else {
                    erros += "\n- Id inválido.";
                }
            } else {
                erros += "\n- Id é obrigatório.";
            }
        }

        // Verificação de outros campos
        if (funcionalidade == Funcionalidade.CADASTRAR || funcionalidade == Funcionalidade.ALTERAR) {
            // Data de início
            if (dataInicioBR == null || dataInicioBR.trim().isEmpty()) {
                erros += "\n- Data de início é obrigatória.";
            } else if (!Utilidades.validarDataBR(dataInicioBR)) {
                erros += "\n- Data de início inválida.";
            }
            //data final
            if (dataFimBR == null || dataFimBR.trim().isEmpty()) {
                erros += "\n- Data de fim é obrigatória.";
            } else if (!Utilidades.validarDataBR(dataFimBR)) {
                erros += "\n- Data de fim inválida.";
            }
            //id de acomodacao
            if (idAcomodacao == null || idAcomodacao.trim().isEmpty()) {
                erros += "\n- Id de acomodação é obrigatória.";
            } else if (!Utilidades.validarNumero(idAcomodacao)) {
                erros += "\n- Id de acomodação inválida.";
            }
            //id cliente responsavel
            if (idClienteResponsavel == null || idClienteResponsavel.trim().isEmpty()) {
                erros += "\n- Id do cliente responsável é obrigatória.";
            } else if (!Utilidades.validarNumero(idClienteResponsavel)) {
                erros += "\n- Id do cliente responsável inválida.";
            }

            //quantidade de hospedes
            if (qtdHospedes == null || qtdHospedes.trim().isEmpty()) {
                erros += "\n- Quantidade de hóspedes é obrigatória.";
            } else if (!Utilidades.validarNumero(qtdHospedes)) {
                erros += "\n- Quantidade de hóspedes inválida.";
            }
        }
        //valor total
        Double valorTotalNumerico = null;
        if (valorTotal != null && !valorTotal.trim().isEmpty()) {
            valorTotalNumerico = Double.parseDouble(valorTotal);
        }
        if (valorTotal != null && !valorTotal.trim().isEmpty()) {
            if (!Utilidades.validarNumero(valorTotal)) {
                erros += "\n- Valor total inválido.";
            }
        }
        // Montagem da mensagem de erro
        String mensagemErro = "";
        if(!erros.isEmpty()) {
            mensagemErro = "Não foi possível " + funcionalidade.name().toLowerCase() + " a reserva! " +
                    "Erro(s) encontrado(s):" + erros;
        }
        return mensagemErro;
    }

}