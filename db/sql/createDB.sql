
CREATE TABLE [Company]
( 
	[Id_Company]         int  NOT NULL ,
	[Company_Name]       varchar(50)  NOT NULL ,
	[Company_PIB]        int  NOT NULL ,
	[Component_Types]    int  NULL ,
	[Component_Types_Present] int  NULL ,
	[Company_Id]         int  NOT NULL  IDENTITY ,
	[Owner_Id]           int  NULL 
)
go

ALTER TABLE [Company]
	ADD CONSTRAINT [XPKCompany] PRIMARY KEY  CLUSTERED ([Id_Company] ASC)
go

ALTER TABLE [Company]
	ADD CONSTRAINT [XAK1Company] UNIQUE ([Company_Name]  ASC)
go

ALTER TABLE [Company]
	ADD CONSTRAINT [XAK3Company] UNIQUE ([Company_PIB]  ASC)
go

CREATE TABLE [Component]
( 
	[Id_Type]            int  NOT NULL ,
	[Id_Component]       int  NOT NULL  IDENTITY ,
	[Name]               varchar(50)  NOT NULL ,
	[Description]        TEXT  NULL 
)
go

ALTER TABLE [Component]
	ADD CONSTRAINT [XPKComponent] PRIMARY KEY  NONCLUSTERED ([Id_Component] ASC)
go

ALTER TABLE [Component]
	ADD CONSTRAINT [XAK1Component] UNIQUE ([Name]  ASC)
go

CREATE TABLE [Component_Type]
( 
	[Id_Type]            int  NOT NULL  IDENTITY ,
	[Name]               varchar(50)  NOT NULL ,
	[Count]              int  NULL 
)
go

ALTER TABLE [Component_Type]
	ADD CONSTRAINT [XPKComponent_Type] PRIMARY KEY  NONCLUSTERED ([Id_Type] ASC)
go

ALTER TABLE [Component_Type]
	ADD CONSTRAINT [XAK1Component_Type] UNIQUE ([Name]  ASC)
go

CREATE TABLE [Ordery]
( 
	[Id_Premium]         int  NOT NULL ,
	[Id_Component]       int  NOT NULL ,
	[Status_Type]        int  NOT NULL ,
	[Date_Order]         datetime  NOT NULL ,
	[Id_Ordery]          int  NOT NULL  IDENTITY ,
	[Id_Company]         int  NOT NULL ,
	[Amount]             integer  NULL 
)
go

ALTER TABLE [Ordery]
	ADD CONSTRAINT [XPKOrder] PRIMARY KEY  NONCLUSTERED ([Id_Ordery] ASC)
go

CREATE TABLE [Premium]
( 
	[Id_Premium]         int  NOT NULL ,
	[Name]               varchar(50)  NOT NULL ,
	[Gender]             bit  NULL ,
	[Reservations]       int  NULL ,
	[Reservations_Failure] int  NULL 
)
go

ALTER TABLE [Premium]
	ADD CONSTRAINT [XPKPremium] PRIMARY KEY  NONCLUSTERED ([Id_Premium] ASC)
go

CREATE TABLE [Status_Type]
( 
	[Id_Type]            int  NOT NULL  IDENTITY ,
	[Name]               varchar(50)  NOT NULL 
)
go

ALTER TABLE [Status_Type]
	ADD CONSTRAINT [XPKStatus_Type] PRIMARY KEY  NONCLUSTERED ([Id_Type] ASC)
go

ALTER TABLE [Status_Type]
	ADD CONSTRAINT [XAK1Status_Type] UNIQUE ([Name]  ASC)
go

CREATE TABLE [Stock]
( 
	[Id_Component]       int  NOT NULL ,
	[Amount]             int  NOT NULL ,
	[Price]              int  NOT NULL ,
	[Id_Company]         int  NOT NULL 
)
go

ALTER TABLE [Stock]
	ADD CONSTRAINT [XPKStock] PRIMARY KEY  NONCLUSTERED ([Id_Component] ASC,[Id_Company] ASC)
go

CREATE TABLE [Users]
( 
	[Id_User]            int  NOT NULL  IDENTITY ,
	[Username]           varchar(50)  NOT NULL ,
	[Password]           varchar(50)  NOT NULL ,
	[Email]              varchar(50)  NOT NULL ,
	[Phone]              varchar(20)  NOT NULL ,
	[Address]            varchar(50)  NOT NULL ,
	[City]               varchar(50)  NOT NULL ,
	[Country]            varchar(50)  NOT NULL 
)
go

ALTER TABLE [Users]
	ADD CONSTRAINT [XPKUser] PRIMARY KEY  NONCLUSTERED ([Id_User] ASC)
go

ALTER TABLE [Users]
	ADD CONSTRAINT [XAK1User] UNIQUE ([Username]  ASC)
go


ALTER TABLE [Company]
	ADD CONSTRAINT [R_3] FOREIGN KEY ([Id_Company]) REFERENCES [Users]([Id_User])
		ON DELETE CASCADE
		ON UPDATE CASCADE
go


ALTER TABLE [Component]
	ADD CONSTRAINT [R_5] FOREIGN KEY ([Id_Type]) REFERENCES [Component_Type]([Id_Type])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Ordery]
	ADD CONSTRAINT [R_14] FOREIGN KEY ([Id_Premium]) REFERENCES [Premium]([Id_Premium])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Ordery]
	ADD CONSTRAINT [R_15] FOREIGN KEY ([Id_Component],[Id_Company]) REFERENCES [Stock]([Id_Component],[Id_Company])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Ordery]
	ADD CONSTRAINT [R_20] FOREIGN KEY ([Status_Type]) REFERENCES [Status_Type]([Id_Type])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Premium]
	ADD CONSTRAINT [R_1] FOREIGN KEY ([Id_Premium]) REFERENCES [Users]([Id_User])
		ON DELETE CASCADE
		ON UPDATE CASCADE
go


ALTER TABLE [Stock]
	ADD CONSTRAINT [R_7] FOREIGN KEY ([Id_Component]) REFERENCES [Component]([Id_Component])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Stock]
	ADD CONSTRAINT [R_25] FOREIGN KEY ([Id_Company]) REFERENCES [Company]([Id_Company])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


CREATE TRIGGER tD_Company ON Company FOR DELETE AS
/* ERwin Builtin Trigger */
/* DELETE trigger on Company */
BEGIN
  DECLARE  @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)
    /* ERwin Builtin Trigger */
    /* Company  Stock on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00020ff8", PARENT_OWNER="", PARENT_TABLE="Company"
    CHILD_OWNER="", CHILD_TABLE="Stock"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_25", FK_COLUMNS="Id_Company" */
    IF EXISTS (
      SELECT * FROM deleted,Stock
      WHERE
        /*  %JoinFKPK(Stock,deleted," = "," AND") */
        Stock.Id_Company = deleted.Id_Company
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete Company because Stock exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* Users  Company on child delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Users"
    CHILD_OWNER="", CHILD_TABLE="Company"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_3", FK_COLUMNS="Id_Company" */
    IF EXISTS (SELECT * FROM deleted,Users
      WHERE
        /* %JoinFKPK(deleted,Users," = "," AND") */
        deleted.Id_Company = Users.Id_User AND
        NOT EXISTS (
          SELECT * FROM Company
          WHERE
            /* %JoinFKPK(Company,Users," = "," AND") */
            Company.Id_Company = Users.Id_User
        )
    )
    BEGIN
      SELECT @errno  = 30010,
             @errmsg = 'Cannot delete last Company because Users exists.'
      GOTO error
    END


    /* ERwin Builtin Trigger */
    RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go


CREATE TRIGGER tU_Company ON Company FOR UPDATE AS
/* ERwin Builtin Trigger */
/* UPDATE trigger on Company */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insId_Company int,
           @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* ERwin Builtin Trigger */
  /* Company  Stock on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="0002498b", PARENT_OWNER="", PARENT_TABLE="Company"
    CHILD_OWNER="", CHILD_TABLE="Stock"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_25", FK_COLUMNS="Id_Company" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(Id_Company)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,Stock
      WHERE
        /*  %JoinFKPK(Stock,deleted," = "," AND") */
        Stock.Id_Company = deleted.Id_Company
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update Company because Stock exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* Users  Company on child update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Users"
    CHILD_OWNER="", CHILD_TABLE="Company"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_3", FK_COLUMNS="Id_Company" */
  IF
    /* %ChildFK(" OR",UPDATE) */
    UPDATE(Id_Company)
  BEGIN
    SELECT @nullcnt = 0
    SELECT @validcnt = count(*)
      FROM inserted,Users
        WHERE
          /* %JoinFKPK(inserted,Users) */
          inserted.Id_Company = Users.Id_User
    /* %NotnullFK(inserted," IS NULL","select @nullcnt = count(*) from inserted where"," AND") */
    
    IF @validcnt + @nullcnt != @numrows
    BEGIN
      SELECT @errno  = 30007,
             @errmsg = 'Cannot update Company because Users does not exist.'
      GOTO error
    END
  END


  /* ERwin Builtin Trigger */
  RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go




CREATE TRIGGER tD_Component ON Component FOR DELETE AS
/* ERwin Builtin Trigger */
/* DELETE trigger on Component */
BEGIN
  DECLARE  @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)
    /* ERwin Builtin Trigger */
    /* Component  Stock on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00024a37", PARENT_OWNER="", PARENT_TABLE="Component"
    CHILD_OWNER="", CHILD_TABLE="Stock"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_7", FK_COLUMNS="Id_Component" */
    IF EXISTS (
      SELECT * FROM deleted,Stock
      WHERE
        /*  %JoinFKPK(Stock,deleted," = "," AND") */
        Stock.Id_Component = deleted.Id_Component
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete Component because Stock exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* Component_Type  Component on child delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Component_Type"
    CHILD_OWNER="", CHILD_TABLE="Component"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_5", FK_COLUMNS="Id_Type" */
    IF EXISTS (SELECT * FROM deleted,Component_Type
      WHERE
        /* %JoinFKPK(deleted,Component_Type," = "," AND") */
        deleted.Id_Type = Component_Type.Id_Type AND
        NOT EXISTS (
          SELECT * FROM Component
          WHERE
            /* %JoinFKPK(Component,Component_Type," = "," AND") */
            Component.Id_Type = Component_Type.Id_Type
        )
    )
    BEGIN
      SELECT @errno  = 30010,
             @errmsg = 'Cannot delete last Component because Component_Type exists.'
      GOTO error
    END


    /* ERwin Builtin Trigger */
    RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go


CREATE TRIGGER tU_Component ON Component FOR UPDATE AS
/* ERwin Builtin Trigger */
/* UPDATE trigger on Component */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insId_Component int,
           @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* ERwin Builtin Trigger */
  /* Component  Stock on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00028101", PARENT_OWNER="", PARENT_TABLE="Component"
    CHILD_OWNER="", CHILD_TABLE="Stock"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_7", FK_COLUMNS="Id_Component" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(Id_Component)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,Stock
      WHERE
        /*  %JoinFKPK(Stock,deleted," = "," AND") */
        Stock.Id_Component = deleted.Id_Component
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update Component because Stock exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* Component_Type  Component on child update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Component_Type"
    CHILD_OWNER="", CHILD_TABLE="Component"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_5", FK_COLUMNS="Id_Type" */
  IF
    /* %ChildFK(" OR",UPDATE) */
    UPDATE(Id_Type)
  BEGIN
    SELECT @nullcnt = 0
    SELECT @validcnt = count(*)
      FROM inserted,Component_Type
        WHERE
          /* %JoinFKPK(inserted,Component_Type) */
          inserted.Id_Type = Component_Type.Id_Type
    /* %NotnullFK(inserted," IS NULL","select @nullcnt = count(*) from inserted where"," AND") */
    
    IF @validcnt + @nullcnt != @numrows
    BEGIN
      SELECT @errno  = 30007,
             @errmsg = 'Cannot update Component because Component_Type does not exist.'
      GOTO error
    END
  END


  /* ERwin Builtin Trigger */
  RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go




CREATE TRIGGER tD_Component_Type ON Component_Type FOR DELETE AS
/* ERwin Builtin Trigger */
/* DELETE trigger on Component_Type */
BEGIN
  DECLARE  @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)
    /* ERwin Builtin Trigger */
    /* Component_Type  Component on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00011716", PARENT_OWNER="", PARENT_TABLE="Component_Type"
    CHILD_OWNER="", CHILD_TABLE="Component"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_5", FK_COLUMNS="Id_Type" */
    IF EXISTS (
      SELECT * FROM deleted,Component
      WHERE
        /*  %JoinFKPK(Component,deleted," = "," AND") */
        Component.Id_Type = deleted.Id_Type
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete Component_Type because Component exists.'
      GOTO error
    END


    /* ERwin Builtin Trigger */
    RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go


CREATE TRIGGER tU_Component_Type ON Component_Type FOR UPDATE AS
/* ERwin Builtin Trigger */
/* UPDATE trigger on Component_Type */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insId_Type int,
           @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* ERwin Builtin Trigger */
  /* Component_Type  Component on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="0001327b", PARENT_OWNER="", PARENT_TABLE="Component_Type"
    CHILD_OWNER="", CHILD_TABLE="Component"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_5", FK_COLUMNS="Id_Type" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(Id_Type)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,Component
      WHERE
        /*  %JoinFKPK(Component,deleted," = "," AND") */
        Component.Id_Type = deleted.Id_Type
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update Component_Type because Component exists.'
      GOTO error
    END
  END


  /* ERwin Builtin Trigger */
  RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go




CREATE TRIGGER tD_Ordery ON Ordery FOR DELETE AS
/* ERwin Builtin Trigger */
/* DELETE trigger on Ordery */
BEGIN
  DECLARE  @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)
    /* ERwin Builtin Trigger */
    /* Status_Type  Ordery on child delete no action */
    /* ERWIN_RELATION:CHECKSUM="0003aad0", PARENT_OWNER="", PARENT_TABLE="Status_Type"
    CHILD_OWNER="", CHILD_TABLE="Ordery"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_20", FK_COLUMNS="Status_Type" */
    IF EXISTS (SELECT * FROM deleted,Status_Type
      WHERE
        /* %JoinFKPK(deleted,Status_Type," = "," AND") */
        deleted.Status_Type = Status_Type.Id_Type AND
        NOT EXISTS (
          SELECT * FROM Ordery
          WHERE
            /* %JoinFKPK(Ordery,Status_Type," = "," AND") */
            Ordery.Status_Type = Status_Type.Id_Type
        )
    )
    BEGIN
      SELECT @errno  = 30010,
             @errmsg = 'Cannot delete last Ordery because Status_Type exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* Stock  Ordery on child delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Stock"
    CHILD_OWNER="", CHILD_TABLE="Ordery"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_15", FK_COLUMNS="Id_Component""Id_Company" */
    IF EXISTS (SELECT * FROM deleted,Stock
      WHERE
        /* %JoinFKPK(deleted,Stock," = "," AND") */
        deleted.Id_Component = Stock.Id_Component AND
        deleted.Id_Company = Stock.Id_Company AND
        NOT EXISTS (
          SELECT * FROM Ordery
          WHERE
            /* %JoinFKPK(Ordery,Stock," = "," AND") */
            Ordery.Id_Component = Stock.Id_Component AND
            Ordery.Id_Company = Stock.Id_Company
        )
    )
    BEGIN
      SELECT @errno  = 30010,
             @errmsg = 'Cannot delete last Ordery because Stock exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* Premium  Ordery on child delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Premium"
    CHILD_OWNER="", CHILD_TABLE="Ordery"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_14", FK_COLUMNS="Id_Premium" */
    IF EXISTS (SELECT * FROM deleted,Premium
      WHERE
        /* %JoinFKPK(deleted,Premium," = "," AND") */
        deleted.Id_Premium = Premium.Id_Premium AND
        NOT EXISTS (
          SELECT * FROM Ordery
          WHERE
            /* %JoinFKPK(Ordery,Premium," = "," AND") */
            Ordery.Id_Premium = Premium.Id_Premium
        )
    )
    BEGIN
      SELECT @errno  = 30010,
             @errmsg = 'Cannot delete last Ordery because Premium exists.'
      GOTO error
    END


    /* ERwin Builtin Trigger */
    RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go


CREATE TRIGGER tU_Ordery ON Ordery FOR UPDATE AS
/* ERwin Builtin Trigger */
/* UPDATE trigger on Ordery */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insId_Ordery int,
           @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* ERwin Builtin Trigger */
  /* Status_Type  Ordery on child update no action */
  /* ERWIN_RELATION:CHECKSUM="00041b5b", PARENT_OWNER="", PARENT_TABLE="Status_Type"
    CHILD_OWNER="", CHILD_TABLE="Ordery"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_20", FK_COLUMNS="Status_Type" */
  IF
    /* %ChildFK(" OR",UPDATE) */
    UPDATE(Status_Type)
  BEGIN
    SELECT @nullcnt = 0
    SELECT @validcnt = count(*)
      FROM inserted,Status_Type
        WHERE
          /* %JoinFKPK(inserted,Status_Type) */
          inserted.Status_Type = Status_Type.Id_Type
    /* %NotnullFK(inserted," IS NULL","select @nullcnt = count(*) from inserted where"," AND") */
    
    IF @validcnt + @nullcnt != @numrows
    BEGIN
      SELECT @errno  = 30007,
             @errmsg = 'Cannot update Ordery because Status_Type does not exist.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* Stock  Ordery on child update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Stock"
    CHILD_OWNER="", CHILD_TABLE="Ordery"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_15", FK_COLUMNS="Id_Component""Id_Company" */
  IF
    /* %ChildFK(" OR",UPDATE) */
    UPDATE(Id_Component) OR
    UPDATE(Id_Company)
  BEGIN
    SELECT @nullcnt = 0
    SELECT @validcnt = count(*)
      FROM inserted,Stock
        WHERE
          /* %JoinFKPK(inserted,Stock) */
          inserted.Id_Component = Stock.Id_Component and
          inserted.Id_Company = Stock.Id_Company
    /* %NotnullFK(inserted," IS NULL","select @nullcnt = count(*) from inserted where"," AND") */
    
    IF @validcnt + @nullcnt != @numrows
    BEGIN
      SELECT @errno  = 30007,
             @errmsg = 'Cannot update Ordery because Stock does not exist.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* Premium  Ordery on child update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Premium"
    CHILD_OWNER="", CHILD_TABLE="Ordery"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_14", FK_COLUMNS="Id_Premium" */
  IF
    /* %ChildFK(" OR",UPDATE) */
    UPDATE(Id_Premium)
  BEGIN
    SELECT @nullcnt = 0
    SELECT @validcnt = count(*)
      FROM inserted,Premium
        WHERE
          /* %JoinFKPK(inserted,Premium) */
          inserted.Id_Premium = Premium.Id_Premium
    /* %NotnullFK(inserted," IS NULL","select @nullcnt = count(*) from inserted where"," AND") */
    
    IF @validcnt + @nullcnt != @numrows
    BEGIN
      SELECT @errno  = 30007,
             @errmsg = 'Cannot update Ordery because Premium does not exist.'
      GOTO error
    END
  END


  /* ERwin Builtin Trigger */
  RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go




CREATE TRIGGER tD_Premium ON Premium FOR DELETE AS
/* ERwin Builtin Trigger */
/* DELETE trigger on Premium */
BEGIN
  DECLARE  @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)
    /* ERwin Builtin Trigger */
    /* Premium  Ordery on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00021c76", PARENT_OWNER="", PARENT_TABLE="Premium"
    CHILD_OWNER="", CHILD_TABLE="Ordery"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_14", FK_COLUMNS="Id_Premium" */
    IF EXISTS (
      SELECT * FROM deleted,Ordery
      WHERE
        /*  %JoinFKPK(Ordery,deleted," = "," AND") */
        Ordery.Id_Premium = deleted.Id_Premium
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete Premium because Ordery exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* Users  Premium on child delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Users"
    CHILD_OWNER="", CHILD_TABLE="Premium"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_1", FK_COLUMNS="Id_Premium" */
    IF EXISTS (SELECT * FROM deleted,Users
      WHERE
        /* %JoinFKPK(deleted,Users," = "," AND") */
        deleted.Id_Premium = Users.Id_User AND
        NOT EXISTS (
          SELECT * FROM Premium
          WHERE
            /* %JoinFKPK(Premium,Users," = "," AND") */
            Premium.Id_Premium = Users.Id_User
        )
    )
    BEGIN
      SELECT @errno  = 30010,
             @errmsg = 'Cannot delete last Premium because Users exists.'
      GOTO error
    END


    /* ERwin Builtin Trigger */
    RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go


CREATE TRIGGER tU_Premium ON Premium FOR UPDATE AS
/* ERwin Builtin Trigger */
/* UPDATE trigger on Premium */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insId_Premium int,
           @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* ERwin Builtin Trigger */
  /* Premium  Ordery on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="000259ed", PARENT_OWNER="", PARENT_TABLE="Premium"
    CHILD_OWNER="", CHILD_TABLE="Ordery"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_14", FK_COLUMNS="Id_Premium" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(Id_Premium)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,Ordery
      WHERE
        /*  %JoinFKPK(Ordery,deleted," = "," AND") */
        Ordery.Id_Premium = deleted.Id_Premium
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update Premium because Ordery exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* Users  Premium on child update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Users"
    CHILD_OWNER="", CHILD_TABLE="Premium"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_1", FK_COLUMNS="Id_Premium" */
  IF
    /* %ChildFK(" OR",UPDATE) */
    UPDATE(Id_Premium)
  BEGIN
    SELECT @nullcnt = 0
    SELECT @validcnt = count(*)
      FROM inserted,Users
        WHERE
          /* %JoinFKPK(inserted,Users) */
          inserted.Id_Premium = Users.Id_User
    /* %NotnullFK(inserted," IS NULL","select @nullcnt = count(*) from inserted where"," AND") */
    
    IF @validcnt + @nullcnt != @numrows
    BEGIN
      SELECT @errno  = 30007,
             @errmsg = 'Cannot update Premium because Users does not exist.'
      GOTO error
    END
  END


  /* ERwin Builtin Trigger */
  RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go




CREATE TRIGGER tD_Status_Type ON Status_Type FOR DELETE AS
/* ERwin Builtin Trigger */
/* DELETE trigger on Status_Type */
BEGIN
  DECLARE  @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)
    /* ERwin Builtin Trigger */
    /* Status_Type  Ordery on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00011207", PARENT_OWNER="", PARENT_TABLE="Status_Type"
    CHILD_OWNER="", CHILD_TABLE="Ordery"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_20", FK_COLUMNS="Status_Type" */
    IF EXISTS (
      SELECT * FROM deleted,Ordery
      WHERE
        /*  %JoinFKPK(Ordery,deleted," = "," AND") */
        Ordery.Status_Type = deleted.Id_Type
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete Status_Type because Ordery exists.'
      GOTO error
    END


    /* ERwin Builtin Trigger */
    RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go


CREATE TRIGGER tU_Status_Type ON Status_Type FOR UPDATE AS
/* ERwin Builtin Trigger */
/* UPDATE trigger on Status_Type */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insId_Type int,
           @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* ERwin Builtin Trigger */
  /* Status_Type  Ordery on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00012708", PARENT_OWNER="", PARENT_TABLE="Status_Type"
    CHILD_OWNER="", CHILD_TABLE="Ordery"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_20", FK_COLUMNS="Status_Type" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(Id_Type)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,Ordery
      WHERE
        /*  %JoinFKPK(Ordery,deleted," = "," AND") */
        Ordery.Status_Type = deleted.Id_Type
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update Status_Type because Ordery exists.'
      GOTO error
    END
  END


  /* ERwin Builtin Trigger */
  RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go




CREATE TRIGGER tD_Stock ON Stock FOR DELETE AS
/* ERwin Builtin Trigger */
/* DELETE trigger on Stock */
BEGIN
  DECLARE  @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)
    /* ERwin Builtin Trigger */
    /* Stock  Ordery on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="000366b2", PARENT_OWNER="", PARENT_TABLE="Stock"
    CHILD_OWNER="", CHILD_TABLE="Ordery"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_15", FK_COLUMNS="Id_Component""Id_Company" */
    IF EXISTS (
      SELECT * FROM deleted,Ordery
      WHERE
        /*  %JoinFKPK(Ordery,deleted," = "," AND") */
        Ordery.Id_Component = deleted.Id_Component AND
        Ordery.Id_Company = deleted.Id_Company
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete Stock because Ordery exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* Company  Stock on child delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Company"
    CHILD_OWNER="", CHILD_TABLE="Stock"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_25", FK_COLUMNS="Id_Company" */
    IF EXISTS (SELECT * FROM deleted,Company
      WHERE
        /* %JoinFKPK(deleted,Company," = "," AND") */
        deleted.Id_Company = Company.Id_Company AND
        NOT EXISTS (
          SELECT * FROM Stock
          WHERE
            /* %JoinFKPK(Stock,Company," = "," AND") */
            Stock.Id_Company = Company.Id_Company
        )
    )
    BEGIN
      SELECT @errno  = 30010,
             @errmsg = 'Cannot delete last Stock because Company exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* Component  Stock on child delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Component"
    CHILD_OWNER="", CHILD_TABLE="Stock"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_7", FK_COLUMNS="Id_Component" */
    IF EXISTS (SELECT * FROM deleted,Component
      WHERE
        /* %JoinFKPK(deleted,Component," = "," AND") */
        deleted.Id_Component = Component.Id_Component AND
        NOT EXISTS (
          SELECT * FROM Stock
          WHERE
            /* %JoinFKPK(Stock,Component," = "," AND") */
            Stock.Id_Component = Component.Id_Component
        )
    )
    BEGIN
      SELECT @errno  = 30010,
             @errmsg = 'Cannot delete last Stock because Component exists.'
      GOTO error
    END


    /* ERwin Builtin Trigger */
    RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go


CREATE TRIGGER tU_Stock ON Stock FOR UPDATE AS
/* ERwin Builtin Trigger */
/* UPDATE trigger on Stock */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insId_Component int, 
           @insId_Company int,
           @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* ERwin Builtin Trigger */
  /* Stock  Ordery on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="0003bf81", PARENT_OWNER="", PARENT_TABLE="Stock"
    CHILD_OWNER="", CHILD_TABLE="Ordery"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_15", FK_COLUMNS="Id_Component""Id_Company" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(Id_Component) OR
    UPDATE(Id_Company)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,Ordery
      WHERE
        /*  %JoinFKPK(Ordery,deleted," = "," AND") */
        Ordery.Id_Component = deleted.Id_Component AND
        Ordery.Id_Company = deleted.Id_Company
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update Stock because Ordery exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* Company  Stock on child update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Company"
    CHILD_OWNER="", CHILD_TABLE="Stock"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_25", FK_COLUMNS="Id_Company" */
  IF
    /* %ChildFK(" OR",UPDATE) */
    UPDATE(Id_Company)
  BEGIN
    SELECT @nullcnt = 0
    SELECT @validcnt = count(*)
      FROM inserted,Company
        WHERE
          /* %JoinFKPK(inserted,Company) */
          inserted.Id_Company = Company.Id_Company
    /* %NotnullFK(inserted," IS NULL","select @nullcnt = count(*) from inserted where"," AND") */
    
    IF @validcnt + @nullcnt != @numrows
    BEGIN
      SELECT @errno  = 30007,
             @errmsg = 'Cannot update Stock because Company does not exist.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* Component  Stock on child update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Component"
    CHILD_OWNER="", CHILD_TABLE="Stock"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_7", FK_COLUMNS="Id_Component" */
  IF
    /* %ChildFK(" OR",UPDATE) */
    UPDATE(Id_Component)
  BEGIN
    SELECT @nullcnt = 0
    SELECT @validcnt = count(*)
      FROM inserted,Component
        WHERE
          /* %JoinFKPK(inserted,Component) */
          inserted.Id_Component = Component.Id_Component
    /* %NotnullFK(inserted," IS NULL","select @nullcnt = count(*) from inserted where"," AND") */
    
    IF @validcnt + @nullcnt != @numrows
    BEGIN
      SELECT @errno  = 30007,
             @errmsg = 'Cannot update Stock because Component does not exist.'
      GOTO error
    END
  END


  /* ERwin Builtin Trigger */
  RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go




CREATE TRIGGER tD_Users ON Users FOR DELETE AS
/* ERwin Builtin Trigger */
/* DELETE trigger on Users */
BEGIN
  DECLARE  @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)
    /* ERwin Builtin Trigger */
    /* Users  Company on parent delete cascade */
    /* ERWIN_RELATION:CHECKSUM="00018b0f", PARENT_OWNER="", PARENT_TABLE="Users"
    CHILD_OWNER="", CHILD_TABLE="Company"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_3", FK_COLUMNS="Id_Company" */
    DELETE Company
      FROM Company,deleted
      WHERE
        /*  %JoinFKPK(Company,deleted," = "," AND") */
        Company.Id_Company = deleted.Id_User

    /* ERwin Builtin Trigger */
    /* Users  Premium on parent delete cascade */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Users"
    CHILD_OWNER="", CHILD_TABLE="Premium"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_1", FK_COLUMNS="Id_Premium" */
    DELETE Premium
      FROM Premium,deleted
      WHERE
        /*  %JoinFKPK(Premium,deleted," = "," AND") */
        Premium.Id_Premium = deleted.Id_User


    /* ERwin Builtin Trigger */
    RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go


CREATE TRIGGER tU_Users ON Users FOR UPDATE AS
/* ERwin Builtin Trigger */
/* UPDATE trigger on Users */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insId_User int,
           @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* ERwin Builtin Trigger */
  /* Users  Company on parent update cascade */
  /* ERWIN_RELATION:CHECKSUM="0002c17a", PARENT_OWNER="", PARENT_TABLE="Users"
    CHILD_OWNER="", CHILD_TABLE="Company"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_3", FK_COLUMNS="Id_Company" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(Id_User)
  BEGIN
    IF @numrows = 1
    BEGIN
      SELECT @insId_User = inserted.Id_User
        FROM inserted
      UPDATE Company
      SET
        /*  %JoinFKPK(Company,@ins," = ",",") */
        Company.Id_Company = @insId_User
      FROM Company,inserted,deleted
      WHERE
        /*  %JoinFKPK(Company,deleted," = "," AND") */
        Company.Id_Company = deleted.Id_User
    END
    ELSE
    BEGIN
      SELECT @errno = 30006,
             @errmsg = 'Cannot cascade Users update because more than one row has been affected.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* Users  Premium on parent update cascade */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Users"
    CHILD_OWNER="", CHILD_TABLE="Premium"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_1", FK_COLUMNS="Id_Premium" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(Id_User)
  BEGIN
    IF @numrows = 1
    BEGIN
      SELECT @insId_User = inserted.Id_User
        FROM inserted
      UPDATE Premium
      SET
        /*  %JoinFKPK(Premium,@ins," = ",",") */
        Premium.Id_Premium = @insId_User
      FROM Premium,inserted,deleted
      WHERE
        /*  %JoinFKPK(Premium,deleted," = "," AND") */
        Premium.Id_Premium = deleted.Id_User
    END
    ELSE
    BEGIN
      SELECT @errno = 30006,
             @errmsg = 'Cannot cascade Users update because more than one row has been affected.'
      GOTO error
    END
  END


  /* ERwin Builtin Trigger */
  RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go


