-- ================================================
-- Template generated from Template Explorer using:
-- Create Procedure (New Menu).SQL
--
-- Use the Specify Values for Template Parameters 
-- command (Ctrl-Shift-M) to fill in the parameter 
-- values below.
--
-- This block of comments will not be included in
-- the definition of the procedure.
-- ================================================
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE sp_getBestUsernames
	@Count int output
AS
BEGIN
	SELECT [Username]
	FROM [dbo].[Users]
	WHERE [Id_User] IN 
		(SELECT [Id_Premium], MAX([Reservations] - [Reservations_Failure]) 
		FROM [dbo].[Premium] 
		GROUP BY [Id_Premium])
END
GO
