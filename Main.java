import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        
        ArrayList<Usuario> usuarios = new ArrayList<>();

        int proximoId = 1;

        int opcao = -1;

        while (opcao != 0) {

            System.out.println("\n===== SISTEMA DE CADASTRO =====");
           
            mostrarQuantidadeUsuarios(usuarios);
            
            System.out.println("1. Cadastrar");
            System.out.println("2. Listar");
            System.out.println("3. Excluir");
            System.out.println("0. Sair");

            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {

                case 1:

                    Usuario usuario = new Usuario();

                    usuario.id = proximoId;
                    proximoId++;

                    System.out.print("Digite o nome: ");
                    scanner.nextLine();
                    usuario.nome = scanner.nextLine();

                    System.out.print("Digite a idade: ");
                    usuario.idade = scanner.nextInt();

                    usuarios.add(usuario);

                    System.out.println("Usuário cadastrado: " + usuario.nome);

                    break;

                case 2:

                    System.out.println("\n===== USUÁRIOS =====");

                    mostrarQuantidadeUsuarios(usuarios);

                    for (Usuario usuarioCadastrado : usuarios) { //Para cada Usuario que estiver dentro da lista usuarios, coloque esse usuário temporariamente na variável usuarioCadastrado.
                        System.out.println("ID: " + usuarioCadastrado.id);
                        System.out.println("Nome: " + usuarioCadastrado.nome);
                        System.out.println("Idade: " + usuarioCadastrado.idade);
                        System.out.println("--------------------");
                    }

                    break;

                case 3:

                    System.out.println("\n===== EXCLUIR USUÁRIO =====");

                    for (Usuario usuarioCadastrado : usuarios) {
                        System.out.println("ID: " + usuarioCadastrado.id + ", Nome: " + usuarioCadastrado.nome);
                    }

                    System.out.println("Digite o ID do usuário que deseja excluir.");
                    System.out.println("Digite 0 para cancelar.");

                    int idExcluir = scanner.nextInt();

                    if (idExcluir == 0) {
                        System.out.println("Exclusão cancelada.");
                        break;
                    }

                    int posicaoUsuario = -1;

                    for (int i = 0; i < usuarios.size(); i++) {

                        if (usuarios.get(i).id == idExcluir) {
                            posicaoUsuario = i;
                            break;
                        }
                    }

                    if (posicaoUsuario != -1) {

                        usuarios.remove(posicaoUsuario);

                        System.out.println("Usuário excluído com sucesso!");

                    } else {

                        System.out.println("Usuário não encontrado.");
                    }

                    break;

                case 0:
                    System.out.println("Programa encerrado.");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        }

        scanner.close();
    }

    static void mostrarQuantidadeUsuarios(ArrayList<Usuario> usuarios) {

        if (usuarios.size() > 0) {
            System.out.println("Usuários cadastrados: " + usuarios.size());
        }
    }
}