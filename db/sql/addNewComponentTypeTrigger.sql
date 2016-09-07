USE [SABDB]
GO
/****** Object:  Trigger [dbo].[componentTypeTrigger]    Script Date: 7/9/2015 1:05:36 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE TRIGGER [dbo].[addComponentTypeTrigger] 
   ON [dbo].[Component_Type] 
   AFTER INSERT
AS 
BEGIN
	
	UPDATE [dbo].[Company] SET [Component_Types] = (SELECT COUNT(*) FROM [dbo].[Component_Type])

END
