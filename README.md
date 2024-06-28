# Proyecto de TDS

## Estructura global del proyecto

El proyecto será desarrollado usando un Diseño Orientado al Dominio de la siguiente forma:

```none
|- appMusic
|- CargadorCanciones
```
## IMPORANTE: PASOS A REALIZAR ANTES DE EJECUTAR EL LANZADOR DEL PROYECTO

## Instrucciones para lanzar la base de datos

Para configurar y lanzar la base de datos MySQL necesaria para este proyecto, sigue estos pasos:

### 1. Configuración del `persistence.xml`

Debes modificar el archivo `persistence.xml` para indicar el nombre del esquema, así como las credenciales de acceso a MySQL. Asegúrate de actualizar los siguientes parámetros:

```xml
<persistence>
    <persistence-unit name="your-persistence-unit">
        <properties>
            <property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/tu_nombre_de_esquema" />
            <property name="javax.persistence.jdbc.user" value="tu_usuario" />
            <property name="javax.persistence.jdbc.password" value="tu_contraseña" />
            <property name="javax.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver" />
        </properties>
    </persistence-unit>
</persistence>
```

Asegúrate de reemplazar:
    tu_nombre_de_esquema con el nombre del esquema que vas a utilizar.
    tu_usuario con tu nombre de usuario de MySQL.
    tu_contraseña con tu contraseña de MySQL.

### 2. Lanzar el servidor local de MySQL

Para lanzar el servidor local de MySQL, sigue estos pasos:

1. Abre una terminal o línea de comandos.
2. Inicia el servidor MySQL utilizando el siguiente comando (asegúrate de que MySQL está instalado y configurado correctamente en tu máquina):

```bash
mysql.server start
```

3. Si utilizas otra herramienta o método para iniciar MySQL, asegúrate de que el servidor esté corriendo en el puerto 3306 (o el puerto que hayas configurado).

### 3.  Crear el esquema en MySQL Workbench
Para crear el esquema en MySQL Workbench, sigue estos pasos:
1. Abre MySQL Workbench.
2. Conéctate a tu servidor MySQL local.
3. En el menú de la izquierda, haz clic derecho en "Schemas" y selecciona "Create Schema..."
4. Introduce el nombre del esquema que especificaste en el archivo persistence.xml y haz clic en "Apply".
5. Revisa y ejecuta el script de creación del esquema.
