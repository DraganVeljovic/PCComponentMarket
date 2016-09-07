-- ================================================
-- Template generated from Template Explorer using:
-- Create Trigger (New Menu).SQL
--
-- Use the Specify Values for Template Parameters 
-- command (Ctrl-Shift-M) to fill in the parameter 
-- values below.
--
-- See additional Create Trigger templates for more
-- examples of different Trigger statements.
--
-- This block of comments will not be included in
-- the definition of the function.
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
CREATE TRIGGER addOrderyTrigger 
   ON [dbo].[Ordery]
   AFTER INSERT
AS 
BEGIN
	DECLARE @premium_id int

	SELECT @premium_id = [Id_Premium]
	FROM inserted	

	UPDATE [dbo].[Premium] SET [Reservations] = ([Reservations] + 1) WHERE [Id_Premium] = @premium_id

END
GO
