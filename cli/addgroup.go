package main

import (
	"fmt"
	"log"
	"net/http"
	"time"

	"github.com/urfave/cli/v2"
)

// Make a user a member of a group
func addgroup(ctx *cli.Context) error {

	serverurl := ctx.String("serverurl")
	user := ctx.String("user")
	password := ctx.String("password")

	group := ctx.String("group")
	member := ctx.String("member")

	client := &http.Client{
		Timeout: 15 * time.Second,
	}
	url := fmt.Sprintf("%s/rest/user/%s/group/%s", serverurl, member, group)
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
