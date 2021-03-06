USE [SABDB]
GO
/****** Object:  Trigger [dbo].[tr_addStock]    Script Date: 7/11/2015 12:06:04 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
ALTER TRIGGER [dbo].[tr_addStock]
   ON  [dbo].[Stock] 
   AFTER INSERT
AS 
BEGIN
	DECLARE @companyId int
	DECLARE @componentId int
	DECLARE @componentTypes int
	DECLARE @components int

	SELECT @companyId = [Id_Company], @componentId = [Id_Component]
	FROM inserted
	
	SELECT @components = COUNT(*) FROM Stock WHERE Id_Company = @companyId
	
	SELECT DISTINCT @componentTypes = COUNT(Id_Type) FROM Stock INNER JOIN Component
	ON Stock.Id_Component = Component.Id_Component
	WHERE Id_Company = @companyId
	
	UPDATE Company SET Component_Types = @componentTypes, Component_Types_Present = @components 
	WHERE Id_Company = @companyId
 
END
