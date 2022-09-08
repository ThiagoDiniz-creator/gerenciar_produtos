package cli;

import model.Produto;

import java.util.ArrayList;
import java.util.Scanner;

public class GerenciarProdutos {
    private final ArrayList<Produto> produtos = new ArrayList<>();

    public static void main(String[] args) {
        GerenciarProdutos gp = new GerenciarProdutos();
        Scanner sc = new Scanner(System.in);

        int opt = 0;
        while (opt != 5) {
            exibirMenu();

            opt = Integer.parseInt(sc.nextLine());
            switch (opt) {
                // Cadastro de produto.
                case 1:
                    gp.addProduto(cadastroInput(sc));
                    break;
                // Entrada de produtos.
                case 2:
                    gp.printProdutosComCodigo();
                    Produto prod = getProdutoByCodigo(gp, sc);

                    if (!prod.getNome().equals("PRODUTO_PADRÃO")) {
                        int qtd = getQtd(sc, prod);

                        prod.addQuantidade(qtd);
                        System.out.printf("%d novos itens (%s) adicionados. Estoque atual: %d.\n", qtd, prod.getNome(),
                                prod.getQuantidade());
                    }
                    break;
                // Saída de produtos.
                case 3:
                    gp.printProdutosComCodigo();
                    Produto prod1 = getProdutoByCodigo(gp, sc);

                    if (!prod1.getNome().equals("PRODUTO_PADRÃO")) {
                        int qtd1 = getQtd(sc, prod1);

                        boolean saida = prod1.removeQuantidade(qtd1);

                        if (saida)
                            System.out.printf("%d itens (%s) foram retirados. Estoque atual: %d.\n", qtd1, prod1.getNome(),
                                    prod1.getQuantidade());
                        else
                            System.out.printf("O estoque atual (%d) não permite a retirada de %d itens.",
                                    prod1.getQuantidade(), qtd1);
                    }

                    break;
                // Estoque atual
                case 4:
                    getEstoqueAtual(gp);
                    break;
                case 5:
                    System.out.println("FINALIZANDO SISTEMA!");
                    break;
                default:
                    System.out.println(opt + " não é um número válido");
                    break;
            }
        }
    }

    private static int getQtd(Scanner sc, Produto prod) {
        System.out.printf("Digite a quantidade de itens (%s): ", prod.getNome());
        return Integer.parseInt(sc.nextLine());
    }

    private static Produto getProdutoByCodigo(GerenciarProdutos gp, Scanner sc) {
        System.out.println("Digite o código do produto: ");
        int input = Integer.parseInt(sc.nextLine());

        if (gp.produtos.size() > input)
            return gp.produtos.get(input);
        else
            return new Produto();
    }

    private static void getEstoqueAtual(GerenciarProdutos gp) {
        double total = 0;
        for (Produto prod : gp.produtos) {
            System.out.printf("%s -- Preço: %.2f -- Quantidade: %d -- Valor estimado:" +
                    " %.2f\n", prod.getNome(), prod.getPreco(), prod.getQuantidade(), prod.getTotal());
            total += prod.getTotal();
        }
        System.out.println("Valor total estimado no estoque: " + total);
    }

    private static Produto cadastroInput(Scanner sc) {
        Produto produto = new Produto();
        System.out.println("Digite o nome do produto: ");
        produto.setNome(sc.nextLine());
        System.out.printf("Digite o preço do produto (%s): ", produto.getNome());
        produto.setPreco(Double.parseDouble(sc.nextLine()));

        return produto;
    }

    private static void exibirMenu() {
        System.out.println("Sistema de gerenciamento de produtos");
        System.out.println("1 => Cadastrar produtos;");
        System.out.println("2 => Entrada de produtos;");
        System.out.println("3 => Saída de produtos;");
        System.out.println("4 => Estoque atual;");
        System.out.println("5 => Sair.");
        System.out.println("Digite a operação desejada: ");
    }

    public void printProdutosComCodigo() {
        int i = 0;
        for (Produto prod : this.produtos) {
            System.out.printf("Código: %d -- Produto: %s -- Quantidade: %d \n", i, prod.getNome(),
                    prod.getQuantidade());

            i++;
        }
    }

    public void addProduto(Produto produto) {
        boolean status = this.produtos.add(produto);
        if (status)
            System.out.println("Produto adicionado com sucesso!");
        else
            System.out.println("Não foi possível adicionar o produto, tente novamente com outros valores!");
    }
}