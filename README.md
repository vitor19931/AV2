# AV2 - sistema-equipamentos

----- INSTRUÇÕES DE EXECUÇÃO E CONFIGURAÇÃO DA CONEXÃO -----

1. Inicie o MySQL no XAMPP e execute banco.sql no MySQL Workbench.
2. Edite Conexao.java, se necessário, com sua senha do MySQL.
3. Abra a pasta do projeto no VS Code com extensões Java/Maven.
4. Aguarde o Maven carregar as dependências do projeto.
5. Execute Main.java para iniciar o sistema.
6. Utilize o menu para cadastrar, listar, pesquisar, alterar e excluir equipamentos.
7. Selecione a opção Sair para encerrar o sistema.

----- Organização da arquitetura MVC -----
Model: Contém Equipamento.java, Computador.java e Projetor.java. 
View: Contém EquipamentoView.java. 
Controller: Contém EquipamentoController.java.
DAO: Contém EquipamentoDAO.java. 
Conexão: Contém Conexao.java.

----- Modelagem do banco -----
Foi usada uma tabela única (equipamento) com a coluna tipo, que indica se o registro é COMPUTADOR ou PROJETOR. Computador e projetor compartilham quase todos os atributos, então a tabela única evita JOINs e simplifica o cadastro, a listagem e as pesquisas. Os atributos específicos ficam em colunas separadas (memoria_ram e luminosidade) e permanecem vazios quando não se aplicam ao tipo. Restrições CHECK no banco garantem que cada tipo preencha apenas o seu atributo.

----- Observações -----
O banco de dados precisa estar em execução para que as operações de cadastro, consulta, alteração e exclusão funcionem. O número de patrimônio é utilizado como chave primária para identificar cada equipamento. Existem dois tipos de equipamentos: computador e projetor, cada um com seu atributo específico (memória RAM em GB e luminosidade em lúmens). A situação deve ser Disponível, Em uso ou Em manutenção. O MySQL deve ser da versão 8.0.16 ou superior, para que as restrições CHECK funcionem. Em caso de erro de conexão, verifique o endereço do servidor, a porta, o nome do banco, o usuário e a senha configurados no arquivo Conexao.java.
