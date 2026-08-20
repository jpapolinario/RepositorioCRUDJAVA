import java.util.ArrayList; // importa a arraylist para criar listas que podem crescer, usa para guardar vários usuarios cadastrados
import java.util.Scanner; // importa o Scanner, uma classe que le o que a pessoa digita
import java.time.LocalDate; // guarda uma data pura
import java.time.Period; // serve para medir a distância de tempo entre duas datas

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
            System.out.println("4. Atualizar");
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

                case 4:
                    atualizarUsuario(scanner, usuarios);
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

        System.out.println("\n===== CADASTRO DE USUÁRIO =====");
        System.out.println("Campos obrigatórios:");
        System.out.println("- Nome");
        System.out.println("- Data de nascimento");
        System.out.println("- CPF");
        System.out.println("- Sexo");
        System.out.println("- Estado civil");
        System.out.println("- Nome do cônjuge (se casado)");
        System.out.println("- Endereço");
        System.out.println("- CEP");
        System.out.println("- Cidade");
        System.out.println("- Estado");
        System.out.println("- Complemento");
        System.out.println("- E-mail");
        System.out.println("\nDigite SAIR em qualquer campo para cancelar o cadastro.");
        System.out.println("==============================");

        while (true) {

            String nome = lerCampo(scanner, "Digite o nome: ");

            if (nome == null) {
                System.out.println("Cadastro cancelado.");
                return false;
            }

            if (validarNome(nome)) {
                usuario.nome = nome;
                break;
            }

            System.out.println("Nome inválido. Digite novamente.");
        }

        while (true) {

            String dataNascimento = lerCampo(scanner, "Digite a data de nascimento (dd/MM/aaaa): ");

            if (dataNascimento == null) {
                System.out.println("Cadastro cancelado.");
                return false;
            }

            if (validarDataNascimento(dataNascimento)) {
                usuario.dataNascimento = dataNascimento;
                break;
            }

            System.out.println("Data inválida. Digite novamente.");
        }

        usuario.idade = calcularIdade(usuario.dataNascimento);

        System.out.println("Idade: " + usuario.idade);

        while (true) {

            String cpf = lerCampo(scanner, "Digite o CPF: ");

            if (cpf == null) {
                System.out.println("Cadastro cancelado.");
                return false;
            }

            if (!validarCpf(cpf)) {
                System.out.println("CPF inválido. Digite novamente.");
                continue;
            }

            if (cpfJaExiste(cpf, usuarios)) {
                System.out.println("Este CPF já está cadastrado.");
                continue;
            }

            usuario.cpf = cpf;
            break;
        }

        while (true) {

            String sexo = lerCampo(scanner, "Digite o sexo: ");

            if (sexo == null) {
                System.out.println("Cadastro cancelado.");
                return false;
            }

            usuario.sexo = sexo;
            break;

        }

        String estadoCivil = lerCampo(scanner, "Digite o estado civil: ");

        if (estadoCivil == null) {
            System.out.println("Cadastro cancelado.");
            return false;
        }

        usuario.estadoCivil = estadoCivil;

        if (estadoCivil.equalsIgnoreCase("casado") ||
                estadoCivil.equalsIgnoreCase("casada")) {

            String conjuge = lerCampo(scanner, "Digite o nome do cônjuge: ");

            if (conjuge == null) {
                System.out.println("Cadastro cancelado.");
                return false;
            }

            while (!validarNome(conjuge)) {

                System.out.println("Nome do cônjuge inválido.");

                conjuge = lerCampo(scanner, "Digite o nome do cônjuge: ");

                if (conjuge == null) {
                    System.out.println("Cadastro cancelado.");
                    return false;
                }
            }

            usuario.conjuge = conjuge;

        } else {

            usuario.conjuge = "";
        }

        String endereco = lerCampo(scanner, "Digite o endereço: ");

        if (endereco == null) {
            System.out.println("Cadastro cancelado.");
            return false;
        }

        usuario.endereco = endereco;

        while (true) {

            String cep = lerCampo(scanner, "Digite o CEP: ");

            if (cep == null) {
                System.out.println("Cadastro cancelado.");
                return false;
            }

            if (validarCep(cep)) {
                usuario.cep = cep;
                break;
            }

            System.out.println("CEP inválido. Digite novamente.");
        }

        String cidade = lerCampo(scanner, "Digite a cidade: ");

        if (cidade == null) {
            System.out.println("Cadastro cancelado.");
            return false;
        }

        usuario.cidade = cidade;

        String estado = lerCampo(scanner, "Digite o estado: ");

        if (estado == null) {
            System.out.println("Cadastro cancelado.");
            return false;
        }

        usuario.estado = estado;

        String complemento = lerCampo(scanner, "Digite o complemento: ");

        if (complemento == null) {
            System.out.println("Cadastro cancelado.");
            return false;
        }

        usuario.complemento = complemento;

        while (true) {

            String email = lerCampo(scanner, "Digite o e-mail: ");

            if (email == null) {
                System.out.println("Cadastro cancelado.");
                return false;
            }

            if (validarEmail(email)) {
                usuario.email = email;
                break;
            }

            System.out.println("E-mail inválido. Digite novamente.");
        }

        usuarios.add(usuario);

        System.out.println("Usuário cadastrado: " + usuario.nome);

        return true;
    }

    static void listarUsuarios(ArrayList<Usuario> usuarios) {

        System.out.println("\n===== USUÁRIOS =====");

        if (usuarios.isEmpty()) {
            System.out.println("Não existem usuários cadastrados.");
            return;
        }

        mostrarQuantidadeUsuarios(usuarios);

        for (Usuario usuario : usuarios) {

            System.out.println("\nID: " + usuario.id);
            System.out.println("Nome: " + usuario.nome);
            System.out.println("Data de nascimento: " + usuario.dataNascimento);
            System.out.println("Idade: " + usuario.idade);
            System.out.println("CPF: " + usuario.cpf);
            System.out.println("Sexo: " + usuario.sexo);
            System.out.println("Estado civil: " + usuario.estadoCivil);
            System.out.println("Cônjuge: " + usuario.conjuge);
            System.out.println("Endereço: " + usuario.endereco);
            System.out.println("CEP: " + usuario.cep);
            System.out.println("Cidade: " + usuario.cidade);
            System.out.println("Estado: " + usuario.estado);
            System.out.println("Complemento: " + usuario.complemento);
            System.out.println("E-mail: " + usuario.email);
            System.out.println("--------------------");
        }
    }

    static void excluirUsuario(Scanner scanner, ArrayList<Usuario> usuarios) {

        System.out.println("\n===== EXCLUIR USUÁRIO =====");

        if (usuarios.isEmpty()) {
            System.out.println("Não existem usuários cadastrados.");
            return;
        }

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

    static void atualizarUsuario(Scanner scanner, ArrayList<Usuario> usuarios) {

        System.out.println("\n===== ATUALIZAR USUÁRIO =====");

        if (usuarios.isEmpty()) {
            System.out.println("Não existem usuários cadastrados.");
            return;
        }

        // Mostra os usuários disponíveis
        for (Usuario usuario : usuarios) {
            System.out.println("ID: " + usuario.id + ", Nome: " + usuario.nome);
        }

        System.out.print("Digite o ID do usuário que deseja atualizar ou 0 para cancelar: ");
        int idAtualizar = scanner.nextInt();
        scanner.nextLine(); // pega o enter

        if (idAtualizar == 0) {
            System.out.println("Atualização cancelada.");
            return;
        }

        // Procura o usuário
        Usuario usuarioEncontrado = null;

        for (Usuario usuario : usuarios) {

            if (usuario.id == idAtualizar) {
                usuarioEncontrado = usuario;
                break;
            }
        }

        if (usuarioEncontrado == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        System.out.println("\nUsuário encontrado: ");

        // Mostra todas as informações do usuário
        System.out.println("\n===== DADOS ATUAIS =====");
        System.out.println("ID: " + usuarioEncontrado.id);
        System.out.println("Nome: " + usuarioEncontrado.nome);
        System.out.println("Data de nascimento: " + usuarioEncontrado.dataNascimento);
        System.out.println("Idade: " + usuarioEncontrado.idade);
        System.out.println("CPF: " + usuarioEncontrado.cpf);
        System.out.println("Sexo: " + usuarioEncontrado.sexo);
        System.out.println("Estado civil: " + usuarioEncontrado.estadoCivil);
        System.out.println("Cônjuge: " + usuarioEncontrado.conjuge);
        System.out.println("Endereço: " + usuarioEncontrado.endereco);
        System.out.println("CEP: " + usuarioEncontrado.cep);
        System.out.println("Cidade: " + usuarioEncontrado.cidade);
        System.out.println("Estado: " + usuarioEncontrado.estado);
        System.out.println("Complemento: " + usuarioEncontrado.complemento);
        System.out.println("E-mail: " + usuarioEncontrado.email);
        System.out.println("========================");

        System.out.println("\nDigite MANTER em qualquer campo para manter o valor atual.");
        System.out.println("Digite SAIR em qualquer campo para cancelar toda a atualização.");

        // Cria uma cópia temporária
        Usuario atualizado = new Usuario();

        atualizado.id = usuarioEncontrado.id;
        atualizado.nome = usuarioEncontrado.nome;
        atualizado.dataNascimento = usuarioEncontrado.dataNascimento;
        atualizado.idade = usuarioEncontrado.idade;
        atualizado.cpf = usuarioEncontrado.cpf;
        atualizado.sexo = usuarioEncontrado.sexo;
        atualizado.estadoCivil = usuarioEncontrado.estadoCivil;
        atualizado.conjuge = usuarioEncontrado.conjuge;
        atualizado.endereco = usuarioEncontrado.endereco;
        atualizado.cep = usuarioEncontrado.cep;
        atualizado.cidade = usuarioEncontrado.cidade;
        atualizado.estado = usuarioEncontrado.estado;
        atualizado.complemento = usuarioEncontrado.complemento;
        atualizado.email = usuarioEncontrado.email;

        while (true) {

            String nome = lerCampo(scanner, "Novo nome: ");

            if (nome == null) {
                System.out.println("Atualização cancelada.");
                return;
            }

            if (nome.equalsIgnoreCase("MANTER")) {
                break;
            }

            if (validarNome(nome)) {
                atualizado.nome = nome;
                break;
            }

            System.out.println("Nome inválido. Atualização cancelada.");
        }

        while (true) {

            String dataNascimento = lerCampo(scanner, "Nova data de nascimento (dd/MM/aaaa): ");

            if (dataNascimento == null) {
                System.out.println("Atualização cancelada.");
                return;
            }

            if (dataNascimento.equalsIgnoreCase("MANTER")) {
                break;
            }

            if (validarDataNascimento(dataNascimento)) {

                atualizado.dataNascimento = dataNascimento;
                atualizado.idade = calcularIdade(dataNascimento);

                break;
            }

            System.out.println("Data inválida. Digite novamente.");
        }

        while (true) {

            String cpf = lerCampo(scanner, "Novo CPF: ");

            if (cpf == null) {
                System.out.println("Atualização cancelada.");
                return;
            }

            if (cpf.equalsIgnoreCase("MANTER")) {
                break;
            }

            if (!validarCpf(cpf)) {
                System.out.println("CPF inválido. Digite novamente.");
                continue;
            }

            if (cpfJaExisteEmOutroUsuario(cpf, usuarios, atualizado.id)) {
                System.out.println("Este CPF já pertence a outro usuário.");
                continue;
            }

            atualizado.cpf = cpf;
            break;
        }

        while (true) {

            String sexo = lerCampo(scanner, "Novo sexo: ");

            if (sexo == null) {
                System.out.println("Atualização cancelada.");
                return;
            }

            if (sexo.equalsIgnoreCase("MANTER")) {
                break;
            }

            if (!sexo.trim().isEmpty()) {
                atualizado.sexo = sexo;
                break;
            }

            System.out.println("Sexo inválido. Digite novamente.");
        }

        while (true) {

            String estadoCivil = lerCampo(scanner, "Novo estado civil: ");

            if (estadoCivil == null) {
                System.out.println("Atualização cancelada.");
                return;
            }

            if (estadoCivil.equalsIgnoreCase("MANTER")) {
                break;
            }

            if (!estadoCivil.trim().isEmpty()) {

                atualizado.estadoCivil = estadoCivil;

                // Se deixar de ser casado, não precisa de cônjuge
                if (!estadoCivil.equalsIgnoreCase("casado") &&
                        !estadoCivil.equalsIgnoreCase("casada")) {

                    atualizado.conjuge = "";
                }

                break;
            }

            System.out.println("Estado civil inválido. Digite novamente.");
        }

        if (atualizado.estadoCivil.equalsIgnoreCase("casado") ||
                atualizado.estadoCivil.equalsIgnoreCase("casada")) {

            while (true) {

                String conjuge = lerCampo(scanner, "Novo nome do cônjuge: ");

                if (conjuge == null) {
                    System.out.println("Atualização cancelada.");
                    return;
                }

                if (conjuge.equalsIgnoreCase("MANTER")) {
                    break;
                }

                if (validarNome(conjuge)) {
                    atualizado.conjuge = conjuge;
                    break;
                }

                System.out.println("Nome inválido. Digite novamente.");
            }
        }

        while (true) {

            String endereco = lerCampo(scanner, "Novo endereço: ");

            if (endereco == null) {
                System.out.println("Atualização cancelada.");
                return;
            }

            if (endereco.equalsIgnoreCase("MANTER")) {
                break;
            }

            if (!endereco.trim().isEmpty()) {
                atualizado.endereco = endereco;
                break;
            }

            System.out.println("Endereço inválido. Digite novamente.");
        }

        while (true) {

            String cep = lerCampo(scanner, "Novo CEP: ");

            if (cep == null) {
                System.out.println("Atualização cancelada.");
                return;
            }

            if (cep.equalsIgnoreCase("MANTER")) {
                break;
            }

            if (validarCep(cep)) {
                atualizado.cep = cep;
                break;
            }

            System.out.println("CEP inválido. Digite novamente.");
        }

        while (true) {

            String cidade = lerCampo(scanner, "Nova cidade: ");

            if (cidade == null) {
                System.out.println("Atualização cancelada.");
                return;
            }

            if (cidade.equalsIgnoreCase("MANTER")) {
                break;
            }

            if (!cidade.trim().isEmpty()) {
                atualizado.cidade = cidade;
                break;
            }

            System.out.println("Cidade inválida. Digite novamente.");
        }

        while (true) {

            String estado = lerCampo(scanner, "Novo estado: ");

            if (estado == null) {
                System.out.println("Atualização cancelada.");
                return;
            }

            if (estado.equalsIgnoreCase("MANTER")) {
                break;
            }

            if (!estado.trim().isEmpty()) {
                atualizado.estado = estado;
                break;
            }

            System.out.println("Estado inválido. Digite novamente.");
        }

        while (true) {

            String complemento = lerCampo(scanner, "Novo complemento: ");

            if (complemento == null) {
                System.out.println("Atualização cancelada.");
                return;
            }

            if (complemento.equalsIgnoreCase("MANTER")) {
                break;
            }

            if (!complemento.trim().isEmpty()) {
                atualizado.complemento = complemento;
                break;
            }

            System.out.println("Complemento inválido. Digite novamente.");
        }

        while (true) {

            String email = lerCampo(scanner, "Novo e-mail: ");

            if (email == null) {
                System.out.println("Atualização cancelada.");
                return;
            }

            if (email.equalsIgnoreCase("MANTER")) {
                break;
            }

            if (validarEmail(email)) {
                atualizado.email = email;
                break;
            }

            System.out.println("E-mail inválido. Digite novamente.");
        }

        usuarioEncontrado.nome = atualizado.nome;
        usuarioEncontrado.dataNascimento = atualizado.dataNascimento;
        usuarioEncontrado.idade = atualizado.idade;
        usuarioEncontrado.cpf = atualizado.cpf;
        usuarioEncontrado.sexo = atualizado.sexo;
        usuarioEncontrado.estadoCivil = atualizado.estadoCivil;
        usuarioEncontrado.conjuge = atualizado.conjuge;
        usuarioEncontrado.endereco = atualizado.endereco;
        usuarioEncontrado.cep = atualizado.cep;
        usuarioEncontrado.cidade = atualizado.cidade;
        usuarioEncontrado.estado = atualizado.estado;
        usuarioEncontrado.complemento = atualizado.complemento;
        usuarioEncontrado.email = atualizado.email;

        System.out.println("Usuário atualizado com sucesso!");
    }

    static boolean validarNome(String nome) {

        nome = nome.trim();

        if (nome.isEmpty()) {
            return false;
        }

        return nome.matches("[\\p{L} ]+");
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

        String[] partes = dataNAscimento.split("/");

        int dia = Integer.parseInt(partes[0]);
        int mes = Integer.parseInt(partes[1]);
        int ano = Integer.parseInt(partes[2]);

        LocalDate nascimento = LocalDate.of(ano, mes, dia);
        LocalDate hoje = LocalDate.now();

        return Period.between(nascimento, hoje).getYears();
    }

    static boolean validarDataNascimento(String data) {

        if (!data.contains("/")) {
            System.out.println("A data deve ser digitada com barras.");
            System.out.println("Exemplo: 18/05/2008");
            return false;
        }

        if (!data.matches("\\d{2}/\\d{2}/\\d{4}")) {
            return false;
        }

        try { // tente executar este bloco
            String[] partes = data.split("/");

            int dia = Integer.parseInt(partes[0]);
            int mes = Integer.parseInt(partes[1]);
            int ano = Integer.parseInt(partes[2]);

            LocalDate nascimento = LocalDate.of(ano, mes, dia);

            if (nascimento.isAfter(LocalDate.now())) {
                return false;
            }

            return true;

        } catch (Exception e) { // se algo der errado faça isso
            return false;
        }
    }

    static String lerCampo(Scanner scanner, String mensagem) {

        while (true) {

            System.out.print(mensagem);
            String valor = scanner.nextLine();

            if (valor.equalsIgnoreCase("SAIR")) {

                System.out.print("Tem certeza que deseja sair do cadastro? (S/N): ");
                String confirmacao = scanner.nextLine();

                if (confirmacao.equalsIgnoreCase("S")) {
                    return null;
                }

                if (confirmacao.equalsIgnoreCase("N")) {
                    continue;
                }

                System.out.println("Digite S para sim ou N para não.");
                continue;
            }

            return valor;
        }
    }

    static boolean validarCep(String cep) {

        cep = cep.replaceAll("\\D", "");

        if (cep.length() != 8) {
            return false;
        }

        return true;
    }

    static boolean validarEmail(String email) {

        if (email.trim().isEmpty()) {
            return false;
        }

        if (!email.contains("@")) {
            return false;
        }

        if (!email.contains(".")) {
            return false;
        }

        return true;
    }

    static boolean cpfJaExisteEmOutroUsuario(String cpf, ArrayList<Usuario> usuarios, int idAtual) {

        cpf = cpf.replaceAll("\\D", "");

        for (Usuario usuario : usuarios) {

            String cpfUsuario = usuario.cpf.replaceAll("\\D", "");

            if (usuario.id != idAtual && cpfUsuario.equals(cpf)) {
                return true;
            }
        }

        return false;
    }
}