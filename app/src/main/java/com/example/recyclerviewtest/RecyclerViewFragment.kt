package com.example.recyclerviewtest

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewtest.databinding.FragmentRecyclerViewBinding
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class RecyclerViewFragment : Fragment(), OnClickListener {

    private lateinit var stationAdapter: StationAdapter
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager
    private lateinit var binding: FragmentRecyclerViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onClick(station: DatosEstacion) {
        parentFragmentManager.setFragmentResult(
            "Station", bundleOf("Station" to station)
        )
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, DetailFragment())
            setReorderingAllowed(true)
            addToBackStack(null)
            commit()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stationAdapter = StationAdapter(getStations(), this)
        linearLayoutManager = LinearLayoutManager(context)

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = stationAdapter
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentRecyclerViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun getStations(): MutableList<DatosEstacion>{
        //val stations = mutableListOf<DatosEstacion>()
        //stations.add(DatosEstacion("FGC","Abrera","S4,R5,R50","","3B","29/03/1922","L'estació d'Abrera (PK 33,16) es va inaugurar el 29 de març de l'any 1922 amb l'obertura del tram Martorell-Olesa, que estava emmarcada en la construcció de la línia Martorell-Manresa. Les instal·lacions han estat modificades en diverses ocasions, si bé actualment consta de les dues vies generals amb andanes laterals de 155 metres de longitud. Aquestes andanes estan dotades de marquesines metàl·liques en bona part d'elles, integrades en pantalles acústiques transparents. L'accés a la via 2 es realitza per l'edifici de viatgers original dels anys vint, que està situat a l'esquerra de les vies i és de dues plantes. A la planta inferior d'aquest edifici hi ha el vestíbul, que és totalment diàfan i disposa de màquines de venda de bitllets i de les barreres tarifàries per accedir a l'andana. L'accés a la via 1 es realitza per una entrada independent integrada en la marquesina on s'hi situa una màquina de venda de bitllets i les barreres tarifàries. L'enllaç entre les andanes es realitza per un pas superior fora de la línia de peatge pel costat Olesa, que disposa d'escalinates, rampes i ascensors.","http://www.trenscat.com/metrobaix/images/P070604113.jpg"))
        //stations.add(DatosEstacion("FGC","Àger","RL2","",null,"1949","L'estació d'Àger (PK 54,090) es troba 9 km a l'est del nucli urbà de la població, molt a prop de la cua del pantà de Camarasa i al peu de la carretera C-12. Va iniciar el servei l'any 1949 amb l'obetura del tram de Balaguer a Cellers. L'estació original tenia tres vies, la general, una desviada a l'esquerra i una via morta a la dreta, connectada pel costat Lleida. Hi havia una andana a la dreta de la via general, amb l'edifici de viatgers d'una planta i golfes. La via morta donava servei a un moll de mercaderies cobert. L'any 2000 es va desmantellar la via desviada i la via morta, tot i que mantenint un culató a la banda Lleida connectat ara pel costat la Pobla i es van conservar tots els edificis.\nL'estació d'Àger actual té una sola via amb andana a la dreta (mirant direcció La Pobla). L'andana disposa d'una marquesina-refugi estàndard amb bancs per tal d'aixoplugar els viatgers de les inclemències meteorològiques, enllumenat amb fanals tipus led i d'un punt d'informació unificat dotat d'intèrfon amb el centre de control de la línia. Des del gener de 2018 aquest baixador és una parada facultativa, que cal sol·licitar per tal que el tren hi pari utilitzant el botó corresponent al punt d'informació, que encén un llum i adverteix al maquinista que cal aturar-se o bé a bord del tren per baixar-hi. El 2012 la Fundació Catalana de l'Esplai va adaptar l'entorn com a allotjament de colònies. Va instal·lar al costat de l'estació dos antics cotxes de viatgers sèrie 5000 totalment remodelats sota la denominació \"Vagons d'Àger\" i va aprofitar també l'antic moll de mercaderies de menjador, sala d'estar i sanitaris així com l'edifici de l'estació com a bar i punt d'informació.","http://www.trenscat.com/pobla/images/ager/P160724332.jpg"))
        //stations.add(DatosEstacion("FGC","Aeri de Montserrat","R5,AERI","","4Z","1930","L'estació de Montserrat-Aeri (PK 43,09) va ser inaugurada l'any 1930 just quan es va posar en servei el telefèric entre Monistrol i Montserrat. El baixador actualment té una sola via i dues andanes laterals, si bé només s'usa la de la dreta (sentit Manresa) ja que com que l'estació es troba en corba cap a la dreta, es permet un millor accés al tren, especialment a la porta central de les unitats 213 on a més l'andana descriu dues petites sobreelevacions (pensades per a una composició doble d'unitats 213). L'andana disposa també d'una marquesina per tal de resguardar els viatgers de les inclemències meteorològiques, en la qual hi ha tres bancs i els plafons informatius. El baixador està totalment adaptat a PMR i per accedir a l'aeri cal passar un pas inferior sota la via al qual s'accedeix mitjançant una escala o bé una rampa. Aquesta estació no té edifici de viatgers i només diposa d'una validadora de bitllets situada a l'entrada del pas inferior.","http://www.trenscat.com/metrobaix/images/P6110041.jpg"))
        //stations.add(DatosEstacion("FGC","Alcoletge","RL1,RL2","",null,"1924","L'estació d'Alcoletge (PK 6,589) està situada 1 km a l'oest del nucli urbà i va ser posada en servei el 1924, amb l'obertura del tram entre Lleida i Balaguer. L'estació originalment estava formada per tres vies, la general i una via desviada i una via morta ambdues a l'esquerra, amb una andana al costat dret de la via general on també s'hi ubicava l'edifici de viatgers, de dues plantes i golfes. A la via morta connectada pel costat Lleida hi havia un moll de mercaderies cobert. El 2001, amb la renovació de la via entre Lleida i Balaguer, es van enderrocar els edificis existents i es va traslladar l'estació a la seva ubicació actual, uns metres cap al sud fins a la carrerada de Granyena.\nL'estació actual d'Alcoletge té una sola via i andana a la dreta, mirant cap a La Pobla. L'andana disposa d'una marquesina- refugi estàndard a la línia amb bancs per tal d'aixoplugar els viatgers de les inclemències meteorològiques i d'un punt d'informació unificat dotat d'intèrfon amb el centre de control de la línia. Des del gener de 2018 aquest baixador és una parada facultativa, que cal sol·licitar per tal que el tren hi pari utilitzant el botó corresponent al punt d'informació, que encén un llum i adverteix al maquinista que cal aturar-se o bé a bord del tren per baixar-hi. A l'estació d'Alcoletge durant uns mesos l'any 2016 es donava la curiosa situació de relleu d'un maquinista de FGC per un de renfe per entrar amb les unitats 331 a Lleida Pirineus, donada la falta d'homologació dels nous trens per part d'adif.","http://www.trenscat.com/pobla/images/alcoletge/P170118475.jpg"))
        //stations.add(DatosEstacion("FGC","Almeda","L8,S3,S4,S8,S9,R5,R6,R50,R60","","1","1985","L'estació d'Almeda se situa al PK 5,268 de la línia Magòria - Manresa, al barri Almeda de Cornellà de Llobregat, a la comarca del Baix Llobregat. L'estació de l'Hospitalet original en superfície (PK 5,25) inicialment l'any 1912 només va ser destinada al trànsit de mercaderies de les indústries de l'entorn. El creixement demogràfic de Cornellà i la urbanització de l'entorn de l'estació van propiciar la seva reconversió a estació de viatgers l'any 1948, rebent el 1956 la denominació Almeda- Cornellà. L'estació de viatgers d'Almeda constava de les dues vies generals (vies 1 i 2) amb dues andanes, amb l'edifici de viatgers situat a la dreta de les vies i separat per un carrer. L'estació també tenia una via morta que donava servei al moll de mercaderies. L’any 1976 l’administració central va tirar endavant el projecte de soterrar la línia del carrilet entre Cornellà i plaça Espanya, iniciant-se l’any 1977 les obres entre Cornellà i l’Hospitalet. El 8 de juliol de 1985 s'inaugurava el tram soterrat entre Sant Josep i Cornellà, ja com a Ferrocarrils de la Generalitat de Catalunya, i la nova estació d'Almeda subterrània. L'any 2006 es va posar en servei un segon accés a l'estació pel carrer Sant Ferran, que a la vegada suposava adaptar l'estació a persones amb mobilitat reduïda.\nL'estació actual d'Almeda es troba sota el passeig dels Ferrocarrils Catalans, entre els carrers Dolors Almeda Roig i Vallès. L'estació s'estructura en dos nivells, amb dos vestíbuls independents. L'entrada pel carrer Dolors Almeda i Roig condueix a un vestíbul en el qual hi ha les màquines de venda de bitllets (MAE), entre d'altres dependències. A partir d'aquí hi ha les barreres tarifàries de control d'accés, amb una línia de peatge independent per a cadascuna de les andanes a les quals s'accedeix per una escala fixa. L'entrada pel carrer Sant Ferran té un edifici en superfície en el que s'ubiquen les màquines de venda de bitllets (MAE) i les barreres tarifàries de control d'accés. Des d'aquest punt es baixa a un nivell intermedi mitjançant una escala fixa, una escala mecànica de pujada i un ascensor. El nivell intermedi actua com a distribuïdor cap a les dues andanes, a les quals es pot baixar mitjançant una escala fixa, una escala mecànica de pujada i un ascensor a cadascuna d'elles, en el cas de la via 1 el mateix ascensor que baixa des del nivell vestíbul al carrer. Els trens circulen pel nivell inferior, format per les dues vies generals (vies 1 i 2) amb andanes laterals de 100 metres de longitud.","http://www.trenscat.com/metrobaix/images/almeda/P060314098.jpg"))
        //stations.add(DatosEstacion("FGC","Avinguda Tibidabo","L7","TB","1","1954","L'estació d'Avinguda Tibidabo es situa al PK 1,9 de la línia Gràcia-Av.Tibidabo i té força similituds amb l'estació de Sabadell Rambla. Es va posar en servei el 1954, juntament amb tot el ramal i es situa al subsòl del carrer Balmes, a l'alçada de la plaça Kennedy i a l'inici de l'avinguda del Tibidabo. Té un sol accés des del centre de la plaça Kennedy, amb una escalinata, una escala mecànica i un ascensor. El vestíbul superior disposa de les màquines de venda de bitllets, el pupitre d'atenció al client i les barreres de control d'accés. Des d'aquest vestíbul superior cal baixar al nivell inferior, situat a molta profunditat, i per això hi ha quatre ascensors i una llarga escalinata. El nivell inferior actualment està format per una única via central amb andanes laterals de 66 metres de longitud i els passadissos d'enllaç amb la zona d'arribada dels ascensors i l'escalinata. Antigament la taquilla s'havia situat al nivell inferior i l'andana només tenia 56 metres de llargària, de manera que només admetien trens de dos cotxes.","http://www.trenscat.com/metrovalles/images/avtibidabo/P101007158.jpg"))
        //stations.add(DatosEstacion("FGC","Baixador de Vallvidrera","S1,S2","","1","1916","El Baixador de Vallvidrera es situa al PK 3,8 de la línia Sarrià-Les Planes. El tren va arribar-hi el 28 de novembre de 1916, amb l'entrada en servei del tram de línia Sarrià-Les Planes. L'estació es situa just a la boca nord del túnel de Collserola, disposa de les dues vies generals amb andanes laterals i originalment tenia l'edifici de viatgers a l'esquerra de les vies amb accés des de la carretera de Vallvidrera. Els anys cinquanta del segle XX es va modificar, elevant les andanes i canviant el pas a nivell entre vies per un de superior sobre la boca del túnel. A més, es va substituir l'edifici de viatgers existent per un de nou i petit situat al nou accés a l'estació, així com una sala d'espera a l'andana direcció Barcelona. La distibució d'aleshores és la que es manté actualment, disposant l'estació de l'accés principal des de la carretera de Vallvidrera a través d'una escala i una rampa fins a la zona d'entrada, que té les màquines de venda de bitllets i les barreres tarifàries de control d'accés sota de dues marquesines. El petit edifici de viatgers es manté com a lavabo, entre d'altres dependències. Des de l'esplanada de sobre la boca del túnel de Collserola es pot baixar a les dues andanes per una escalinata i un ascensor a cadascuna d'elles. Les andanes es van prolongar els anys noranta per donar cabuda als trens de quatre cotxes i estan parcialment cobertes per marquesines metàl·liques. Existeix un segon accés a l'estació des de l'escola Vil·la Joana i el barri del Rectoret a l'andana de la via 1, que disposa de barreres tarifàries però no de màquines de venda de bitllets.","http://www.trenscat.com/metrovalles/images/baixadorvallvidrera/P160503075.jpg"))
        //return

        return lista
    }
}