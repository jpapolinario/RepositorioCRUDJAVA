import java.util.ArrayList; // importa a arraylist para criar listas que podem crescer, usa para guardar vários usuarios cadastrados
import java.util.Scanner; // importa o Scanner, uma classe que le o que a pessoa digita
import java.time.LocalDate;
import java.time.Period;

public class Main { // cria a classe principal do programa, chamada main

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in); // cria um objeto scanner, capaz de ler o tecclado, new Scanner cria o
                                                  // leitor, system.in representa a entrada

        ArrayList<Usuario> usuarios = new ArrayList<>(); // você tá criando uma lista chamada usuarios e o trecho
                                                         // <Usuario> diz que ela só pode armazenar objetos do tipo
                                                         // Usuario

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

            System.out.print("Digite o nome ou digite SAIR para cancelar: ");
            usuario.nome = scanner.nextLine();

            if (usuario.nome.equals("SAIR")) { // equals compara textos

                System.out.print("Tem certeza que deseja sair do cadastro? (S/N): ");
                String confirmacao = scanner.nextLine();

                if (confirmacao.equalsIgnoreCase("S")) {// ignoreCase ignora se é maiuscula ou minuscula
                    System.out.println("Cadastro cancelado.");
                    return false;
                }

                continue;
            }

            if (validarNome(usuario.nome)) {
                break;
            }

            System.out.println("Nome inválido. Digite novamente.");
        }

        System.out.print("Digite a data de nascimento (dd/MM/aaaa): ");
        usuario.dataNascimento = scanner.nextLine();

        usuario.idade = calcularIdade(usuario.dataNascimento);

        System.out.println("Idade: " + usuario.idade);

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

        for (Usuario usuarioCadastrado : usuarios) { // para cada Usuario existente dentro da lista usuarios, coloque
                                                     // temporariamente esse usuário na variável usuarioCadastrado
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

        System.out.println("Digite o ID do usuário que deseja excluir ou 0 para cancelar.");

        int idExcluir = scanner.nextInt();

        if (idExcluir == 0) {
            System.out.println("Exclusão cancelada.");
            return;
        }

        int posicaoUsuario = -1;

        for (int i = 0; i < usuarios.size(); i++) {

            if (usuarios.get(i).id == idExcluir) {// usuarios.get(i).id pega o usuario que esta na posição i e acessa o
                                                  // id dele
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

        if (nome.trim().isEmpty()) { // isEmpty ve se ficou vazio
            return false;
        }

        for (int i = 0; i < nome.length(); i++) { // percorrendo pelo nome inteiro

            char caractere = nome.charAt(i); // pega o caractere dessa posição

            if (!Character.isLetter(caractere) && caractere != ' ') {
                return false;
            }
        }

        return true;
    }

    static boolean validarCpf(String cpf) {

        cpf = cpf.replaceAll("\\D", ""); // remove tudo que nao for numero e substitui por nada

        if (cpf.length() != 11) {
            return false;
        }

        if (cpf.matches("(\\d)\\1{10}")) { // impede todos numeros iguais
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

    static int calcularIdade(String dataNAscimento) {

        String[] partes = dataNAscimento.split("/"); // pq as []?

        int dia = Integer.parseInt(partes[0]);// Integer? .parseInt?
        int mes = Integer.parseInt(partes[1]);
        int ano = Integer.parseInt(partes[2]);

        LocalDate nascimento = LocalDate.of(ano, mes, dia);
        LocalDate hoje = LocalDate.now();

        return Period.between(nascimento, hoje).getYears();
    }
}