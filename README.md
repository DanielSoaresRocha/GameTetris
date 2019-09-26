# GameTetris
Implementação do jogo Tetris utilizando androidStudio

Apresentação do Jogo:

Tetris (em russo: Тетрис) é um jogo electrônico muito popular, desenvolvido por Alexey Pajitnov, Dmitry Pavlovsky e Vadim Gerasimov, e lançado em Junho de 1984. Pajitnov e Pavlovsky eram engenheiros informáticos no Centro de Computadores da Academia Russa das Ciências e Vadim era um aluno com 16 anos. Tetris foi um dos primeiros itens de exportação de sucesso da União Soviética e um dos primeiros a ser visto como um tipo de vício.Atingiu um público alvo inédito na história dos videogames. Alexey Pajitnov conheceu o quebra-cabeças Pentaminó e decidiu criar uma versão virtual dele para seu computador Electronica 60. Removeu um dos blocos do jogo e nomeou com o prefixo quatro em grego: Tetris. O jogo consiste em empilhar tetraminós que descem a tela de forma que completem linhas horizontais. Quando uma linha se forma, ela se desintegra, as camadas superiores descem, e o jogador ganha pontos. Quando a pilha de peças chega ao topo da tela, a partida se encerra. FONTE: https://pt.wikipedia.org/wiki/Tetris

	Tela inicial do jogo

Requisitos disponibilizados no jogo:
 1. O aplicativo tem 4 telas: Tela inicial, Tabuleiro, Resultado e Configurações.
 2. Ícone personalizado. 
3. Localização (traduzido) para português-BR (padrão) e inglês-USA.
 4. Suporte a configuração de dificuldades: fácil (descem lentamente), normal e difícil (peças descem em grande velocidade). 
A velocidade normal é escolhida por padrão quando o aplicativo é instalado. 
5. Salvar informações de configuração do jogo.
 6. Guardar informações de “pontuação” e “record de pontuação”. 
7. Usa ImageViews para representar o tabuleiro. 
8. Trata movimento (com threads) e empilhamento de peças. 
9. Trata botão de giro da peça. 
10. Trata botão de acelerar peça.
 11. Tratar aumento da pontuação a cada linha desintegrada. 
12. Tratar fim do jogo quando uma peça atinge o topo

 É invocada a partir do launcher do Android para apresentar a tela inicial do aplicativo com uma imagem e 2 ou 3 botões:
- Novo jogo: Inicia um novo jogo do zero. Nenhum estado anterior é restaurado. Abre a tela do Tabuleiro.
- Continuar: Botão opcional. Deve aparecer apenas se houver um estado de jogo anterior pausado. O jogo poderá ser pausado quando o sistema operacional invoca o método onPause() do ciclo de vida ou quando o usuário aperta o botão “Pause” na tela do tabuleiro. Abre a tela Tabuleiro. 
- Configurações: Abre a tela Configurações onde o usuário poderá selecionar a dificuldade. Caso algum dado seja alterado nessa tela o jogo que estava pausado, se houver, não é perdido.


	Tabuleiro

Activity que é invocada quando o usuário clica no botão “Novo Jogo” ou “Continuar” da tela inicial. Um tabuleiro deve ser montado dinamicamente com 20 blocos de largura e 20 blocos de altura. A parte vazia do tabuleiro é representada por um bloco preto com bordas brancas. Caso o usuário queria continuar um jogo salvo, a Activity recarregar o estado anterior (peça atual, posição da peça atual, peças empilhadas, próxima peça). Caso seja um novo jogo, é gerado aleatoriamente a primeira peça do jogo e a próxima peça. A primeira peça começa no centro e topo do tabuleiro. Tal peça se deslocar gradativamente para a parte de baixo do tabuleiro seguindo a velocidade selecionada.

Essa tela deve possui ainda 5 botões. 
- Girar peça: gira a peça. (dois estados possíveis) 
- Acelerar peça: a peça ganha velocidade na descida 
- Direcional para esquerda: a peça mudar 1 bloco para esquerda. 
- Direcional para direita: a peça deve muda 1 bloco para direita. 
- Pause: salva a peça atual, posição da peça atual, peças empilhadas

Caso a peça atual toque no limite superior do tabuleiro o jogo termina e o usuário é enviado para a tela Resultado. É passado como parâmetro para a tela resultado o valor da pontuação.

	Resultado
Exibição: 
- Pontuação: a pontuação obtida no jogo. 
- Record: a pontuação máxima obtida desde que o aplicativo foi instalado. 
- Novo record: quando a pontuação obtida for maior que o record. 

Além disso, exibir uma imagem representando o fim do jogo e dois botões: 
- Novo jogo: Invoca a tela do tabuleiro com o jogo iniciando do zero. 
- Sair: Finaliza o aplicativo.

