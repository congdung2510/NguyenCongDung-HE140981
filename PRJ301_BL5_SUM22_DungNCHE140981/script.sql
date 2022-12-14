USE [PRJ301_Afternoon_B5_Summer2022]
GO
/****** Object:  Table [dbo].[Account]    Script Date: 8/23/2022 2:38:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Account](
	[aid] [int] IDENTITY(1,1) NOT NULL,
	[ausername] [varchar](200) NOT NULL,
	[apassword] [varchar](200) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[aid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[ausername] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Account_Group]    Script Date: 8/23/2022 2:38:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Account_Group](
	[aid] [int] NOT NULL,
	[gid] [int] NOT NULL,
 CONSTRAINT [pk_account_group] PRIMARY KEY CLUSTERED 
(
	[aid] ASC,
	[gid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Employee]    Script Date: 8/23/2022 2:38:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Employee](
	[eid] [int] NOT NULL,
	[ename] [varchar](150) NOT NULL,
 CONSTRAINT [PK_Employee] PRIMARY KEY CLUSTERED 
(
	[eid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Groups]    Script Date: 8/23/2022 2:38:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Groups](
	[gid] [int] IDENTITY(1,1) NOT NULL,
	[gname] [varchar](200) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[gid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[OnLeave]    Script Date: 8/23/2022 2:38:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[OnLeave](
	[oid] [int] IDENTITY(1,1) NOT NULL,
	[eid] [int] NOT NULL,
	[oOffFrom] [datetime] NOT NULL,
	[oOffTo] [datetime] NOT NULL,
	[reason] [nvarchar](200) NULL,
PRIMARY KEY CLUSTERED 
(
	[oid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Timesheet]    Script Date: 8/23/2022 2:38:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Timesheet](
	[tid] [int] IDENTITY(1,1) NOT NULL,
	[eid] [int] NOT NULL,
	[checkin] [datetime] NOT NULL,
	[checkout] [datetime] NULL,
 CONSTRAINT [PK_Timesheet] PRIMARY KEY CLUSTERED 
(
	[tid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Account_Group]  WITH CHECK ADD  CONSTRAINT [fk_account_group] FOREIGN KEY([aid])
REFERENCES [dbo].[Account] ([aid])
GO
ALTER TABLE [dbo].[Account_Group] CHECK CONSTRAINT [fk_account_group]
GO
ALTER TABLE [dbo].[Account_Group]  WITH CHECK ADD  CONSTRAINT [fk_account_group_group] FOREIGN KEY([gid])
REFERENCES [dbo].[Groups] ([gid])
GO
ALTER TABLE [dbo].[Account_Group] CHECK CONSTRAINT [fk_account_group_group]
GO
ALTER TABLE [dbo].[OnLeave]  WITH CHECK ADD  CONSTRAINT [fk_onleave_employee] FOREIGN KEY([eid])
REFERENCES [dbo].[Employee] ([eid])
GO
ALTER TABLE [dbo].[OnLeave] CHECK CONSTRAINT [fk_onleave_employee]
GO
ALTER TABLE [dbo].[Timesheet]  WITH CHECK ADD  CONSTRAINT [FK_Timesheet_Employee] FOREIGN KEY([eid])
REFERENCES [dbo].[Employee] ([eid])
GO
ALTER TABLE [dbo].[Timesheet] CHECK CONSTRAINT [FK_Timesheet_Employee]
GO
