package main

import (
	"log"
	"os"

	"github.com/urfave/cli/v2"
)

func main() {
	app := &cli.App{
		Name:  "cb-cli",
		Usage: "Codebeamer Command Line Tools",
		Commands: []*cli.Command{
			{
				Name:  "grantrole",
				Usage: "Grant a project role to a user",
				Flags: []cli.Flag{
					&cli.StringFlag{
						Name:     "project",
						Aliases:  []string{"p"},
						Required: true,
					},
					&cli.StringFlag{
						Name:     "role",
						Aliases:  []string{"r"},
						Required: true,
					},
					&cli.StringFlag{
						Name:     "member",
						Aliases:  []string{"m"},
						Required: true,
					},
				},
				Action: grantrole,
			},
		},
		Flags: []cli.Flag{
			&cli.StringFlag{
				Name:    "serverurl",
				Aliases: []string{"s"},
				EnvVars: []string{"CB_CLI_SERVERURL"},
				Value:   "http://localhost:8080",
			},
			&cli.StringFlag{
				Name:    "user",
				Aliases: []string{"u"},
				EnvVars: []string{"CB_CLI_USER"},
				Value:   "bond",
			},
			&cli.StringFlag{
				Name:    "password",
				Aliases: []string{"p"},
				EnvVars: []string{"CB_CLI_PASSWORD"},
				Value:   "007",
			},
		},
	}

	if err := app.Run(os.Args); err != nil {
		log.Fatal(err)
	}
}
