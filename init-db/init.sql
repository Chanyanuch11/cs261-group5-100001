-- Create database only if it doesn't exist
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = N'Bookstore')
BEGIN
    PRINT 'Creating database [Bookstore]...';
    CREATE DATABASE [Bookstore];
END
ELSE
BEGIN
    PRINT 'Database [Bookstore] already exists.';
END
GO

USE [Bookstore];
GO

-- Create table if not exists
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='books' AND xtype='U')
BEGIN
    CREATE TABLE books (
        id BIGINT IDENTITY PRIMARY KEY,
        title NVARCHAR(255),
        author NVARCHAR(255),
        category NVARCHAR(100),
        price FLOAT,
        isbn NVARCHAR(100),
        description NVARCHAR(MAX),
        coverUrl NVARCHAR(255),
        stock INT NOT NULL DEFAULT 0
    );
    PRINT '✅ Table [books] created.';
END
GO