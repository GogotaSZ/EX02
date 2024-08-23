package ti2;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {
        
        DAO dao = new DAO();
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        dao.conectar();

        while (continuar) {
            System.out.println("Escolha uma opção:");
            System.out.println("1. Listar todos os produtos");
            System.out.println("2. Inserir um novo produto");
            System.out.println("3. Atualizar um produto");
            System.out.println("4. Excluir um produto");
            System.out.println("5. Sair");

            int opcao = 0;
            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); // Limpar o buffer do scanner
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, insira um número.");
                scanner.nextLine(); // Limpar o buffer do scanner
                continue;
            }

            switch (opcao) {
                case 1:
                    // Listar todos os produtos
                    System.out.println("==== Listar todos os produtos ====");
                    Produto[] produtos = dao.getProdutos();
                    if (produtos != null && produtos.length > 0) {
                        for (Produto p : produtos) {
                            System.out.println(p.toString());
                        }
                    } else {
                        System.out.println("Nenhum produto encontrado.");
                    }
                    break;

                case 2:
                    // Inserir um novo produto
                    System.out.print("Digite o ID do produto: ");
                    int id = 0;
                    try {
                        id = scanner.nextInt();
                        scanner.nextLine(); // Limpar o buffer
                    } catch (InputMismatchException e) {
                        System.out.println("Entrada inválida para ID. Deve ser um número.");
                        scanner.nextLine(); // Limpar o buffer
                        break;
                    }

                    System.out.print("Digite o nome do produto: ");
                    String nome = scanner.nextLine();
                    
                    System.out.print("Digite o preço do produto: ");
                    double preco = 0;
                    try {
                        preco = scanner.nextDouble();
                        scanner.nextLine(); // Limpar o buffer
                    } catch (InputMismatchException e) {
                        System.out.println("Entrada inválida para preço. Deve ser um número.");
                        scanner.nextLine(); // Limpar o buffer
                        break;
                    }

                    Produto novoProduto = new Produto(id, nome, preco);
                    if (dao.inserirProduto(novoProduto)) {
                        System.out.println("Inserção com sucesso -> " + novoProduto.toString());
                    } else {
                        System.out.println("Falha na inserção do produto.");
                    }
                    break;

                case 3:
                    // Atualizar um produto
                    System.out.print("Digite o ID do produto a ser atualizado: ");
                    int idAtualizar = 0;
                    try {
                        idAtualizar = scanner.nextInt();
                        scanner.nextLine(); // Limpar o buffer
                    } catch (InputMismatchException e) {
                        System.out.println("Entrada inválida para ID. Deve ser um número.");
                        scanner.nextLine(); // Limpar o buffer
                        break;
                    }

                    System.out.print("Digite o novo nome do produto: ");
                    String novoNome = scanner.nextLine();
                    
                    System.out.print("Digite o novo preço do produto: ");
                    double novoPreco = 0;
                    try {
                        novoPreco = scanner.nextDouble();
                        scanner.nextLine(); // Limpar o buffer
                    } catch (InputMismatchException e) {
                        System.out.println("Entrada inválida para preço. Deve ser um número.");
                        scanner.nextLine(); // Limpar o buffer
                        break;
                    }

                    Produto produtoAtualizar = new Produto(idAtualizar, novoNome, novoPreco);
                    if (dao.atualizarProduto(produtoAtualizar)) {
                        System.out.println("Produto atualizado com sucesso -> " + produtoAtualizar.toString());
                    } else {
                        System.out.println("Falha na atualização do produto.");
                    }
                    break;

                case 4:
                    // Excluir um produto
                    System.out.print("Digite o ID do produto a ser excluído: ");
                    int idExcluir = 0;
                    try {
                        idExcluir = scanner.nextInt();
                        scanner.nextLine(); // Limpar o buffer
                    } catch (InputMismatchException e) {
                        System.out.println("Entrada inválida para ID. Deve ser um número.");
                        scanner.nextLine(); // Limpar o buffer
                        break;
                    }

                    if (dao.excluirProduto(idExcluir)) {
                        System.out.println("Produto excluído com sucesso.");
                    } else {
                        System.out.println("Falha na exclusão do produto.");
                    }
                    break;

                case 5:
                    // Sair
                    continuar = false;
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

        dao.close();
        scanner.close();
    }
}
