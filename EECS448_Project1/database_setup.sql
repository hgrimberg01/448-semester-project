-- phpMyAdmin SQL Dump
-- version 3.4.9
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 07, 2012 at 12:26 AM
-- Server version: 5.1.66
-- PHP Version: 5.3.3

SET FOREIGN_KEY_CHECKS=0;
SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `chale`
--

-- --------------------------------------------------------

--
-- Table structure for table `TeamProject_Contacts`
--

CREATE TABLE IF NOT EXISTS `TeamProject_Contacts` (
  `ContactID` int(11) NOT NULL AUTO_INCREMENT,
  `FirstName` text NOT NULL,
  `LastName` text NOT NULL,
  `JobTitle` text NOT NULL,
  `Email` text NOT NULL,
  `WorkPhone` text NOT NULL,
  `HomePhone` text NOT NULL,
  `WorkAddress` text NOT NULL,
  `HomeAddress` text NOT NULL,
  `Birthday` text NOT NULL,
  PRIMARY KEY (`ContactID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `TeamProject_ContactsToDepartments`
--

CREATE TABLE IF NOT EXISTS `TeamProject_ContactsToDepartments` (
  `RowID` int(11) NOT NULL AUTO_INCREMENT,
  `ContactID` int(11) NOT NULL,
  `DepartmentID` int(11) NOT NULL,
  PRIMARY KEY (`RowID`),
  KEY `ContactID` (`ContactID`),
  KEY `DepartmentID` (`DepartmentID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `TeamProject_ContactsToReports`
--

CREATE TABLE IF NOT EXISTS `TeamProject_ContactsToReports` (
  `RowID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ReportID` int(11) unsigned NOT NULL,
  `ContactID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`RowID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `TeamProject_Departments`
--

CREATE TABLE IF NOT EXISTS `TeamProject_Departments` (
  `DepartmentID` int(11) NOT NULL AUTO_INCREMENT,
  `DepartmentName` text NOT NULL,
  `DepartmentEmail` text NOT NULL,
  `DepartmentPhone` text NOT NULL,
  `DepartmentMailingAddress` text NOT NULL,
  PRIMARY KEY (`DepartmentID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `TeamProject_DepartmentsToReports`
--

CREATE TABLE IF NOT EXISTS `TeamProject_DepartmentsToReports` (
  `RowID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `DepartmentID` int(10) unsigned NOT NULL,
  `ReportID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`RowID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `TeamProject_Reports`
--

CREATE TABLE IF NOT EXISTS `TeamProject_Reports` (
  `ReportID` int(11) NOT NULL AUTO_INCREMENT,
  `Title` varchar(255) NOT NULL,
  `StartDate` date NOT NULL,
  `EndDate` date NOT NULL,
  `Dimensions` text NOT NULL,
  `Metrics` text NOT NULL,
  `Filter` text NOT NULL,
  `Sort` text NOT NULL,
  `MaxResults` int(11) NOT NULL,
  PRIMARY KEY (`ReportID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;


