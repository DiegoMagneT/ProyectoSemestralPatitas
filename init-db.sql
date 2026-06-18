CREATE DATABASE IF NOT EXISTS db_usuarios      CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_autenticacion CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_refugios      CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_adopciones    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_mascotas      CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_reportes      CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_publicaciones CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_pedidos       CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

GRANT ALL PRIVILEGES ON db_usuarios.*      TO 'patitas'@'%';
GRANT ALL PRIVILEGES ON db_autenticacion.* TO 'patitas'@'%';
GRANT ALL PRIVILEGES ON db_refugios.*      TO 'patitas'@'%';
GRANT ALL PRIVILEGES ON db_adopciones.*    TO 'patitas'@'%';
GRANT ALL PRIVILEGES ON db_mascotas.*      TO 'patitas'@'%';
GRANT ALL PRIVILEGES ON db_reportes.*      TO 'patitas'@'%';
GRANT ALL PRIVILEGES ON db_publicaciones.* TO 'patitas'@'%';
GRANT ALL PRIVILEGES ON db_pedidos.*       TO 'patitas'@'%';
FLUSH PRIVILEGES;
