/**
 * Disciplina: Teoria dos Grafos
 * 
 * Exercício Prático 02
 * 
 * Grupo:
 * 	Bianca Rangel
 * 	Rafael Guerra de Pontes
 * 	Victor Paz Braga
 * 	Wesley Roseno
 * 
 * Questão 2
	Um gerente de uma rede local de computadores necessita medir o tempo necessário para uma mensagem ser transmitida de uma máquina para outra da rede através de uma conexão direta se existir. Para tal, o gerente enviará um programa (agente móvel) a partir da máquina gerente. Este agente é um programa ativo que deverá percorrer todas as conexões diretas entre duas máquina e registrar o tempo gasto para percorrer cada conexão. A fim de minimizar o tráfego na rede, cada conexão deve ser percorrida uma única vez e o agente só retorna à máquina gerente após percorrer todas as conexões.
	Usando a API JGraphT (jgrapht.org), implemente um programa que recebe o grafo que descreve uma rede e a indicação da máquina gerente como entrada e utiliza conceitos vistos em sala para:
	1- Determinar se é possível encontrar a rota desejada com as restrições acima descritas.
	2- Se sim, retorne a rota.
	Exemplo para teste: rede.gml (em anexo a esta tarefa) - a máquina gerente denomina-se c. 
 * 
 * */

package exercicio.pratico02;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.alg.cycle.HierholzerEulerianCycle;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.io.EdgeProvider;
import org.jgrapht.io.GmlImporter;
import org.jgrapht.io.ImportException;
import org.jgrapht.io.VertexProvider;

public class Questao02 {
	
	public static void main(String[] args) {

		
		// Classes auxiliares para o grafo a ser construído
	    VertexProvider<Object> vp1 = 
	    		(label,attributes) -> new DefaultVertex (label,attributes);
	    EdgeProvider<Object,RelationshipEdge> ep1 = 
	    		(from,to,label,attributes) -> new RelationshipEdge(from,to,attributes);
		GmlImporter <Object,RelationshipEdge> gmlImporter = new GmlImporter <> (vp1,ep1);
	    Graph<Object, RelationshipEdge> graphgml = new SimpleGraph<>(RelationshipEdge.class);
  	    
	    try {
	    	// Importando os dados do grafo a partir do arquivo gml
	    	gmlImporter.importGraph(graphgml, ImportGraph.readFile("./src/exercicio/pratico02/rede.gml"));
		} catch (ImportException e) {
			throw new RuntimeException(e);
		}	
  	    
	    // Exibindo dados do grafo lido para comprovar que deu tudo certo
   	    System.out.println("Grafo importado do arquivo GML com sucesso!");
   	    System.out.println("\nDados sobre o grafo lido:");
	    System.out.println("\nArestas: "+ graphgml.edgeSet());
	    System.out.println("Vértices: " + graphgml.vertexSet());
	    System.out.println();
	    
        HierholzerEulerianCycle<Object, RelationshipEdge> eulerianCycle = new HierholzerEulerianCycle<>();

        List<Object> vList = eulerianCycle.getEulerianCycle(graphgml).getVertexList();
        
        // Feedback sobre a possibilidade da rota desejada
        if (!eulerianCycle.isEulerian(graphgml)) {
        	System.out.println("O grafo não é Euleriano, portanto, não é possível encontrar a rota desejada.");
        	return;
        }
        System.out.println("O grafo é Euleriano, é possível encontrar a rota desejada!\n");
        
        // Variável com o nó de interesse
        String initialNode = "C";
        
        // Varrendo o caminho encontrado em busca do nó inicial
        boolean foundInitialNode = false;
        int initialIndex = 0;
        for(int i = 0; i < vList.toArray().length; i++) {
        	if(!foundInitialNode && vList.toArray()[i].toString().equals(initialNode)) {
        		foundInitialNode = true;
        		initialIndex = i;
        		break;
        	}
        }
        
        // Lista que guardará o ciclo final
        List<String> finalList = new ArrayList<String>();
        
        // Criando uma nova lista de caminhos com o SHIFT desejado
        int iterations = 0;
        int i = initialIndex;
        while(iterations < vList.toArray().length - 1) {
        	String newEdge = "{" + vList.toArray()[i] + ", " + vList.toArray()[(i+1)%vList.toArray().length] + "}";
        	if(i == vList.toArray().length - 1) {
        		i = (i + 1) % vList.toArray().length;
        		continue;
        	}
        	i = (i + 1) % vList.toArray().length;
        	finalList.add(newEdge);
        	iterations++;
        }
        
        // Rota desejada a partir do vértice de interesse
        System.out.println("Partindo do vértice '" + initialNode + "', é possível traçar a seguinte rota euleriana:");
        System.out.println(finalList);
        
	}
	
}
