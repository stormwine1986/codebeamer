package main

import (
	"fmt"
	"log"
	"net/http"
	"time"

	"github.com/urfave/cli/v2"
)

func grantrole(ctx *cli.Context) error {

	serverurl := ctx.String("serverurl")
	user := ctx.String("user")
	password := ctx.String("password")

	project := ctx.String("project")
	role := ctx.String("role")
	member := ctx.String("member")

	client := &http.Client{
		Timeout: 15 * time.Second,
	}
	url := fmt.Sprintf("%s/rest/project/%s/role/%s/user/%s", serverurl, project, role, member)
	req, _ := http.NewRequest(http.MethodPut, url, nil)
	req.SetBasicAuth(user, password)
	resp, err := client.Do(req)
	if err != nil {
		log.Fatal(err)
		return cli.Exit("HttpConnect Error", 1)
	}
	defer resp.Body.Close()
	if resp.StatusCode != http.StatusOK {
		return cli.Exit(resp.StatusCode, 1)
	}

	fmt.Println("Done")
	return nil
}
