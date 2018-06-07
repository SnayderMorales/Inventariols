-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 09-04-2018 a las 20:01:12
-- Versión del servidor: 10.1.28-MariaDB
-- Versión de PHP: 7.1.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `inventario`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ingresos`
--

CREATE TABLE `ingresos` (
  `codigo_ingreso` int(11) NOT NULL,
  `fecha` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Volcado de datos para la tabla `ingresos`
--

INSERT INTO `ingresos` (`codigo_ingreso`, `fecha`) VALUES
(1, '2018-04-07'),
(2, '2018-04-07'),
(3, '2018-04-07');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ingresos_productos`
--

CREATE TABLE `ingresos_productos` (
  `ingresos_codigo_ingreso` int(11) NOT NULL,
  `productos_codigo` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Volcado de datos para la tabla `ingresos_productos`
--

INSERT INTO `ingresos_productos` (`ingresos_codigo_ingreso`, `productos_codigo`, `cantidad`) VALUES
(1, 3, 0),
(1, 4, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `locker`
--

CREATE TABLE `locker` (
  `codigo` int(11) NOT NULL,
  `numero` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Volcado de datos para la tabla `locker`
--

INSERT INTO `locker` (`codigo`, `numero`) VALUES
(1, 10),
(2, 11);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `codigo` int(11) NOT NULL,
  `descripcion` varchar(150) COLLATE utf8_bin NOT NULL,
  `total` int(11) NOT NULL,
  `locker_codigo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`codigo`, `descripcion`, `total`, `locker_codigo`) VALUES
(3, 'A', 10, 1),
(4, 'AB', 12, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `salidas`
--

CREATE TABLE `salidas` (
  `codigo_salida` int(11) NOT NULL,
  `INSTRUCTOR` varchar(50) COLLATE utf8_bin NOT NULL,
  `fecha` date NOT NULL,
  `ficha` varchar(40) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `salidas_productos`
--

CREATE TABLE `salidas_productos` (
  `productos_codigo` int(11) NOT NULL,
  `salidas_codigo_salida` int(11) NOT NULL,
  `cantida` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `ingresos`
--
ALTER TABLE `ingresos`
  ADD PRIMARY KEY (`codigo_ingreso`);

--
-- Indices de la tabla `ingresos_productos`
--
ALTER TABLE `ingresos_productos`
  ADD PRIMARY KEY (`ingresos_codigo_ingreso`,`productos_codigo`),
  ADD KEY `productos_ingresos_fk` (`productos_codigo`);

--
-- Indices de la tabla `locker`
--
ALTER TABLE `locker`
  ADD PRIMARY KEY (`codigo`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`codigo`),
  ADD UNIQUE KEY `descripcion` (`descripcion`),
  ADD KEY `productos_locker_fk` (`locker_codigo`);

--
-- Indices de la tabla `salidas`
--
ALTER TABLE `salidas`
  ADD PRIMARY KEY (`codigo_salida`);

--
-- Indices de la tabla `salidas_productos`
--
ALTER TABLE `salidas_productos`
  ADD PRIMARY KEY (`productos_codigo`,`salidas_codigo_salida`),
  ADD KEY `salidas_productos_salidas_fk` (`salidas_codigo_salida`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `ingresos`
--
ALTER TABLE `ingresos`
  MODIFY `codigo_ingreso` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `locker`
--
ALTER TABLE `locker`
  MODIFY `codigo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `codigo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `salidas`
--
ALTER TABLE `salidas`
  MODIFY `codigo_salida` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `ingresos_productos`
--
ALTER TABLE `ingresos_productos`
  ADD CONSTRAINT `ingreso_productos` FOREIGN KEY (`ingresos_codigo_ingreso`) REFERENCES `ingresos` (`codigo_ingreso`),
  ADD CONSTRAINT `ingresos_productos_fk` FOREIGN KEY (`productos_codigo`) REFERENCES `productos` (`codigo`),
  ADD CONSTRAINT `productos_ingresos_fk` FOREIGN KEY (`productos_codigo`) REFERENCES `productos` (`codigo`);

--
-- Filtros para la tabla `productos`
--
ALTER TABLE `productos`
  ADD CONSTRAINT `productos_locker_fk` FOREIGN KEY (`locker_codigo`) REFERENCES `locker` (`codigo`);

--
-- Filtros para la tabla `salidas_productos`
--
ALTER TABLE `salidas_productos`
  ADD CONSTRAINT `salidas_productos_productos_fk` FOREIGN KEY (`productos_codigo`) REFERENCES `productos` (`codigo`),
  ADD CONSTRAINT `salidas_productos_salidas_fk` FOREIGN KEY (`salidas_codigo_salida`) REFERENCES `salidas` (`codigo_salida`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
