# trabalho-lab-3
Trabalho realizado para a disciplina de Laboratório de Projeto e Análise de Algoritmos

Nas aulas teóricas e práticas, estudamos o conceito de arquivos binários sequenciais, sequenciais de acesso
aleatório e indexados. Vimos um exemplo prático da criação de um arquivo sequencial com registro de tamanho
fixo. Este exercício tem como objetivo colocar em prática estes conhecimentos em uma atividade individual de
laboratório.

Como anexo, você terá acesso a um arquivo de dados em formato texto, contendo mais de 690.000 registros de
dados no formato visto em laboratório: um identificador inteiro (matrícula), um nome (string) e uma nota entre 0
e 100, sendo permitida uma casa decimal. Cada linha do arquivo contém estas informações separadas por pontoe-vírgula (‘;’).
Suas tarefas nestes exercícios são:

- Realizar a leitura do arquivo texto e gravar os dados em um arquivo sequencial binário de acesso
aleatório;
o Após a criação deste arquivo, permitir a busca e exibição dos dados de um aluno por sua posição
no arquivo.
- Criar um índice simples para este arquivo (sugere-se índice baseado na matrícula);
  - O índice pode ser criado com o auxílio do uso de estruturas de memória principal. No entanto, ao
final da execução do programa, este índice deve ser gravado em arquivo para que, na próxima
execução do programa, ele apenas seja lido, sem necessidade de recriação.
  - Com o índice criado, deve ser permitida a busca e exibição dos dados de um aluno por sua
matrícula (ou outro atributo, caso você assim escolha)
- No momento pedido pelo usuário, exibir um relatório em ordem alfabética dos alunos, paginando os
registros de 20 em 20. O relatório também pode ser exibido, a pedido do usuário, a partir de uma posição
específica do arquivo.
  - Para esta tarefa, você precisará realizar a ordenação dos dados do arquivo em memória
secundária e, posteriormente, refazer seu índice. 
