package core.controller;

import base.enumeration.Funcionalidade;
import base.exception.AcomodacaoException;
import base.exception.ClienteException;
import base.exception.PessoaException;
import base.exception.ReservaException;
import core.test.ReservaTest;

import java.util.Scanner;

public class ReservaController {

    //Atributos
    private ReservaTest reservaTest;
    // Construtor
    public ReservaController(ReservaTest reservaTest) {
        this.reservaTest = reservaTest;
    }

    // Gerenciador de testes
    public void iniciar() throws ReservaException, AcomodacaoException, ClienteException {
        Scanner entrada = new Scanner(System.in);
        String opcao = null;

        System.out.println(
                "=== TESTE MODULO PESSOA ===\n"
                        + "1 - Listar\n"
                        + "2 - Cadastrar\n"
                        + "3 - Alterar\n"
                        + "4 - Excluir"
        );

        do {
            System.out.println("\nEscolha a funcionalidade:");
            opcao = entrada.nextLine();

            Funcionalidade funcionalidade = null;
            switch (opcao) {
                case "1":
                    funcionalidade = Funcionalidade.LISTAR;
                    break;
                case "2":
                    funcionalidade = Funcionalidade.CADASTRAR;
                    break;
                case "3":
                    funcionalidade = Funcionalidade.ALTERAR;
                    break;
                case "4":
                    funcionalidade = Funcionalidade.EXCLUIR;
                    break;
            }

            if(funcionalidade != null) {
                try {
                    System.out.println("Funcionalidade: " + funcionalidade);
                    System.out.println(reservaTest.testar(funcionalidade));
                } catch(ReservaException excecao) {
                    System.err.println(excecao.getMessage());
                }
            }

        } while(!opcao.isEmpty());
    }

}
