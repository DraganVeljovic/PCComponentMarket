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
CREATE TRIGGER insertComponentTrigger 
   ON  [dbo].[Component] 
   AFTER INSERT
AS 
BEGIN
	DECLARE @typeId int

	SELECT @typeId = Id_Type FROM inserted

	UPDATE [dbo].[Component_Type] SET Count = Count + 1 WHERE Id_Type = @typeId

END
GO
