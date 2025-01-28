-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 28-01-2025 a las 14:06:50
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `karaoke`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `canciones`
--

CREATE TABLE `canciones` (
  `idCancion` int(11) NOT NULL,
  `nombreCancion` varchar(255) DEFAULT NULL,
  `nombreAutor` varchar(255) DEFAULT NULL,
  `vecesCantada` int(11) DEFAULT NULL,
  `titulo` varchar(255) DEFAULT NULL,
  `tiempo` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `canciones`
--

INSERT INTO `canciones` (`idCancion`, `nombreCancion`, `nombreAutor`, `vecesCantada`, `titulo`, `tiempo`) VALUES
(1, 'Despacito', 'Luis Fonsi', 200, 'Despacito - Version Original', 230),
(2, 'Shape of You', 'Ed Sheeran', 150, 'Shape of You - Radio Edit', 230),
(3, 'Blinding Lights', 'The Weeknd', 180, 'Blinding Lights - Remix', 200),
(4, 'Rolling in the Deep', 'Adele', 130, 'Rolling in the Deep - Extended', 240),
(5, 'Someone Like You', 'Adele', 120, 'Someone Like You - Live', 275),
(6, 'Uptown Funk', 'Mark Ronson ft. Bruno Mars', 250, 'Uptown Funk - Original', 270),
(7, 'Havana', 'Camila Cabello', 140, 'Havana - Remix', 210),
(8, 'Old Town Road', 'Lil Nas X', 300, 'Old Town Road - Remix', 160),
(9, 'Bad Guy', 'Billie Eilish', 220, 'Bad Guy - Radio Edit', 200),
(10, 'Thinking Out Loud', 'Ed Sheeran', 160, 'Thinking Out Loud - Live', 240);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `karaokelog`
--

CREATE TABLE `karaokelog` (
  `idLog` int(11) NOT NULL,
  `idUsuario` int(11) DEFAULT NULL,
  `idCancion` int(11) DEFAULT NULL,
  `fechaRepro` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `idUsuario` int(11) NOT NULL,
  `nombreUsuario` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`idUsuario`, `nombreUsuario`) VALUES
(7, 'Chris'),
(8, 'Jonathan');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `canciones`
--
ALTER TABLE `canciones`
  ADD PRIMARY KEY (`idCancion`);

--
-- Indices de la tabla `karaokelog`
--
ALTER TABLE `karaokelog`
  ADD PRIMARY KEY (`idLog`),
  ADD KEY `idUsuario` (`idUsuario`),
  ADD KEY `idCancion` (`idCancion`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`idUsuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `canciones`
--
ALTER TABLE `canciones`
  MODIFY `idCancion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `karaokelog`
--
ALTER TABLE `karaokelog`
  MODIFY `idLog` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `idUsuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `karaokelog`
--
ALTER TABLE `karaokelog`
  ADD CONSTRAINT `karaokelog_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`idUsuario`),
  ADD CONSTRAINT `karaokelog_ibfk_2` FOREIGN KEY (`idCancion`) REFERENCES `canciones` (`idCancion`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
