package appmusic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import umu.tds.model.Cancion;
import umu.tds.model.EstiloMusical;
import umu.tds.model.Interprete;

/**
 * Clase de prueba para la clase {@link Cancion}.
 */
public class TestCancion {

	private Cancion cancion;
	private List<Interprete> interpretes;
	private EstiloMusical estilo;

	/**
	 * Configura el entorno de prueba inicializando los objetos necesarios.
	 */
	@Before
	public void setUp() {

		Interprete freddie = new Interprete();
		freddie.setNombre("Freddie Mercury");
		Interprete brian = new Interprete();
		brian.setNombre("Brian May");
		interpretes = Arrays.asList(freddie, brian);

		estilo = new EstiloMusical();
		estilo.setNombre("Rock");

		cancion = new Cancion();
		cancion.setId(1);
		cancion.setTitulo("Bohemian Rhapsody");
		cancion.setInterpretes(interpretes);
		cancion.setEstilo(estilo);
		cancion.setNumReproducciones(1000000);
		cancion.setUrl("http://example.com/bohemian_rhapsody");
		cancion.setDuracion(354);
	}

	/**
	 * Prueba que la instancia de la canción no sea nula.
	 */
	@Test
	public void testCancion() {
		assertNotNull("La instancia de la canción no debería ser nula", cancion);
	}

	/**
	 * Prueba el método {@link Cancion#getId()}.
	 */
	@Test
	public void testGetId() {
		assertEquals("El ID de la canción debería ser 1", 1, cancion.getId());
	}

	/**
	 * Prueba el método {@link Cancion#getTitulo()}.
	 */
	@Test
	public void testGetTitulo() {
		assertEquals("El título de la canción debería ser 'Bohemian Rhapsody'", "Bohemian Rhapsody",
				cancion.getTitulo());
	}

	/**
	 * Prueba el método {@link Cancion#getInterpretes()}.
	 */
	@Test
	public void testGetInterpretes() {
		assertEquals("La lista de intérpretes debería tener 2 elementos", 2, cancion.getInterpretes().size());
		assertEquals("El primer intérprete debería ser 'Freddie Mercury'", "Freddie Mercury",
				cancion.getInterpretes().get(0).getNombre());
		assertEquals("El segundo intérprete debería ser 'Brian May'", "Brian May",
				cancion.getInterpretes().get(1).getNombre());
	}

	/**
	 * Prueba el método {@link Cancion#getEstilo()}.
	 */
	@Test
	public void testGetEstilo() {
		assertEquals("El estilo de la canción debería ser 'Rock'", "Rock", cancion.getEstilo().getNombre());
	}

	/**
	 * Prueba el método {@link Cancion#getNumReproducciones()}.
	 */
	@Test
	public void testGetNumReproducciones() {
		assertEquals("El número de reproducciones debería ser 1000000", 1000000, cancion.getNumReproducciones());
	}

	/**
	 * Prueba el método {@link Cancion#getUrl()}.
	 */
	@Test
	public void testGetUrl() {
		assertEquals("La URL de la canción debería ser 'http://example.com/bohemian_rhapsody'",
				"http://example.com/bohemian_rhapsody", cancion.getUrl());
	}

	/**
	 * Prueba el método {@link Cancion#getDuracion()}.
	 */
	@Test
	public void testGetDuracion() {
		assertEquals("La duración de la canción debería ser 354 segundos", 354, cancion.getDuracion());
	}

	/**
	 * Prueba el método {@link Cancion#toString()}.
	 */
	@Test
	public void testToString() {
		String expected = "Cancion [id=1, titulo=Bohemian Rhapsody, interpretes=" + interpretes + ", estilo=" + estilo
				+ ", numReproducciones=1000000, url=http://example.com/bohemian_rhapsody, duracion=354]";
		assertEquals("La representación en cadena de la canción debería ser la esperada", expected, cancion.toString());
	}
}
