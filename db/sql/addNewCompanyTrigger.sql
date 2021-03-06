USE [SABDB]
GO
/****** Object:  Trigger [dbo].[addNewCompanyTrigger]    Script Date: 7/9/2015 1:05:11 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE TRIGGER [dbo].[addNewCompanyTrigger] 
   ON [dbo].[Company]
   AFTER INSERT
AS 
BEGIN
	DECLARE @company_id int
	
	SELECT @company_id = [Company_Id]
	FROM inserted

	UPDATE [dbo].[Company] SET [Component_Types] = (SELECT COUNT(*) FROM [dbo].[Component_Type]) WHERE [Company_Id] = @company_id
	
END
