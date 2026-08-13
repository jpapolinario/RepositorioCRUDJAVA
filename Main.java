import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ArrayList<Usuario> usuarios = new ArrayList<>(); // você tá criando uma lista chamada usuarios e essa lista só
                                                         // pode armazenar objetos do tipo Usuario

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
                    boolean cadastrou = cadastrarUsuario(scanner, usuarios, proximoId);

                    if (cadastrou) {
                        proximoId++;
                    }

                    break;

                case 2:
                    listarUsuarios(usuarios);
                    break;

                case 3:
                    excluirUsuario(scanner, usuarios);
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

    static boolean cadastrarUsuario(Scanner scanner, ArrayList<Usuario> usuarios, int id) {

        Usuario usuario = new Usuario();

        usuario.id = id;

        scanner.nextLine();

        while (true) {

            System.out.print("Digite o nome: ");
            usuario.nome = scanner.nextLine();

            if (validarNome(usuario.nome)) {
                break;
            }

            System.out.println("Nome inválido. Digite novamente.");
        }

        System.out.print("Digite a data de nascimento: ");
        usuario.dataNascimento = scanner.nextLine();

        System.out.print("Digite a idade: ");
        int idade = scanner.nextInt();

        if (!validarIdade(idade)) {
            System.out.println("Idade inválida.");
            return false;
        }

        usuario.idade = idade;

        scanner.nextLine();

        System.out.print("Digite o CPF: ");
        usuario.cpf = scanner.nextLine();

        if (!validarCpf(usuario.cpf)) {
            System.out.println("CPF inválido.");
            return false;
        }

        if (cpfJaExiste(usuario.cpf, usuarios)) {
            System.out.println("Este CPF já está cadastrado.");
            return false;
        }

        System.out.print("Digite o sexo: ");
        usuario.sexo = scanner.nextLine();

        System.out.print("Digite o estado civil: ");
        usuario.estadoCivil = scanner.nextLine();

        System.out.print("Digite o nome do cônjuge: ");
        usuario.conjuge = scanner.nextLine();

        System.out.print("Digite o endereço: ");
        usuario.endereco = scanner.nextLine();

        System.out.print("Digite o CEP: ");
        usuario.cep = scanner.nextLine();

        System.out.print("Digite a cidade: ");
        usuario.cidade = scanner.nextLine();

        System.out.print("Digite o estado: ");
        usuario.estado = scanner.nextLine();

        System.out.print("Digite o complemento: ");
        usuario.complemento = scanner.nextLine();

        System.out.print("Digite o e-mail: ");
        usuario.email = scanner.nextLine();

        usuarios.add(usuario);

        System.out.println("Usuário cadastrado: " + usuario.nome);

        return true;
    }

    static void listarUsuarios(ArrayList<Usuario> usuarios) {

        System.out.println("\n===== USUÁRIOS =====");

        mostrarQuantidadeUsuarios(usuarios);

        for (Usuario usuarioCadastrado : usuarios) {
            System.out.println("ID: " + usuarioCadastrado.id);
            System.out.println("Nome: " + usuarioCadastrado.nome);
            System.out.println("Idade: " + usuarioCadastrado.idade);
            System.out.println("--------------------");
        }
    }

    static void excluirUsuario(Scanner scanner, ArrayList<Usuario> usuarios) {

        System.out.println("\n===== EXCLUIR USUÁRIO =====");

        for (Usuario usuarioCadastrado : usuarios) {
            System.out.println("ID: " + usuarioCadastrado.id + ", Nome: " + usuarioCadastrado.nome);
        }

        System.out.println("Digite o ID do usuário que deseja excluir.");
        System.out.println("Digite 0 para cancelar.");

        int idExcluir = scanner.nextInt();

        if (idExcluir == 0) {
            System.out.println("Exclusão cancelada.");
            return;
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
    }

    static boolean validarIdade(int idade) {

        if (idade < 0 || idade > 122) {
            return false;
        }

        return true;
    }

    static boolean validarNome(String nome) {

        if (nome.trim().isEmpty()) {
            return false;
        }

        for (int i = 0; i < nome.length(); i++) { // percorrendo pelo nome inteiro

            char caractere = nome.charAt(i);

            if (!Character.isLetter(caractere) && caractere != ' ') {
                return false;
            }
        }

        return true;
    }

    static boolean validarCpf(String cpf) {

        cpf = cpf.replaceAll("\\D", "");

        if (cpf.length() != 11) {
            return false;
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        int soma = 0;

        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }

        int resto = (soma * 10) % 11;

        if (resto == 10) {
            resto = 0;
        }

        if (resto != Character.getNumericValue(cpf.charAt(9))) {
            return false;
        }

        soma = 0;

        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }

        resto = (soma * 10) % 11;

        if (resto == 10) {
            resto = 0;
        }

        if (resto != Character.getNumericValue(cpf.charAt(10))) {
            return false;
        }

        return true;
    }

    static boolean cpfJaExiste(String cpf, ArrayList<Usuario> usuarios) {

        cpf = cpf.replaceAll("\\D", "");

        for (Usuario usuario : usuarios) { // Para cada usuário que existe dentro da lista usuarios, coloque
                                           // temporariamente esse usuário na variável usuario

            String cpfUsuario = usuario.cpf.replaceAll("\\D", "");

            if (cpfUsuario.equals(cpf)) {
                return true;
            }
        }

        return false;
    }
}