		// "apiThrottling" : false
		var username = "bond";
		var password = "007";

		// Constants: Tracker ID
		var StakeholerRequirements = 10028;
		var SystemRequirements = 4806;
		var SystemArchitectureRequirements = 4770;
		var SoftwareRequirements = 4779;
		var SoftwareArchitectureRequirements = 4782
		var SoftwareDetailedDesignUnits = 4810;
		var SoftwareUnitTests = 4797;
		var SoftwareIntegrationTests = 4797;
		var SoftwareQualificationTests = 4797;
		var SystemValidationSpecifications = 4815;
		var SystemRequirements_VerificationMethod = `${SystemRequirements}.choiceList[3]`
		var SystemArchitectureRequirements_VerificationMethod = `${SystemArchitectureRequirements}.choiceList[4]`
		var SystemValidationSpecifications_VerificationMethod = `${SystemValidationSpecifications}.choiceList[0]`
		var Problems = 4755;
		var PeerReviews = 4817;
		var Problems_UBV = `${Problems}.choiceList[3]`;
		var Problems_Customer = `${Problems}.choiceList[2]`;
		var PeerReviews_Overdue = `${PeerReviews}.choiceList[7]`;
		
		
		// Stakeholder Requirments Matrix
		// ** Stakeholder Requirments - Total
		$.ajax({
    			type: "POST",
    			url: "../rest/query/page/1",
    			contentType : 'application/json',
    			data: JSON.stringify({queryString:"tracker.id="+StakeholerRequirements}),
    			success: function(data) {
				var total = data.trackerItems.total;
				console.log("Stakeholder Requirments - Total: " + total);
				$("#div002").text(total);
				// ** Stakeholder Requirements - %Implemented 
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({
						queryString:"workItemResolution='Successful' AND tracker.id="+StakeholerRequirements
					}),
    					success: function(data) {
						p = (data.trackerItems.total/total).toFixed(2)*100 + "%";
						console.log("Stakeholder Requirements - %Implemented: " + p);
						$("#div001").text(p);
    					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
				// ** Stakeholder Requirements - Approved
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({queryString:"status='Accepted' AND tracker.id="+StakeholerRequirements}),
    					success: function(data) {
						console.log("Stakeholder Requirements - Approved: " + data.trackerItems.total)
						$("#div003").text(data.trackerItems.total);
    					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
				// ** Stakeholder Requirements - %Decomposed
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({queryString:"downstreamReference = { tracker.id="+SystemRequirements+" } AND tracker.id="+StakeholerRequirements}),
    					success: function(data) {
						p = (data.trackerItems.total/total).toFixed(2)*100 + "%";
						console.log("Stakeholder Requirements - %Decomposed: " + p)
						$("#div004").text(p);
    					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
    			},
    			beforeSend: function (xhr) {
    				xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    			}
		});
						
		// System Requirements Matrix
		// ** System Requirments - Total
		$.ajax({
    			type: "POST",
    			url: "../rest/query/page/1",
    			contentType : 'application/json',
    			data: JSON.stringify({queryString:"tracker.id="+SystemRequirements}),
    			success: function(data) {
				var total = data.trackerItems.total;
				console.log("System Requirments - Total: " + total);
				$("#div006").text(total);
				// ** System Requirements - Approved
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({queryString:"status='Accepted' AND tracker.id="+SystemRequirements}),
    					success: function(data) {
						console.log("System Requirements - Approved: " + data.trackerItems.total);
						$("#div007").text(data.trackerItems.total);
						// System Requirements - %Approved
						p = (data.trackerItems.total/total).toFixed(2)*100 + "%";
						console.log("System Requirements - %Approved: " + p);
						$("#div008").text(p);
    					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
				// ** System Requirement - NFR
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({queryString:"category='Non-functional' AND tracker.id="+SystemRequirements}),
    					success: function(data) {
						console.log("System Requirement - NFR: " + data.trackerItems.total);
						$("#div009").text(data.trackerItems.total);
    					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
				// ** System Requirement - FR
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({queryString:"category='Functional' AND tracker.id="+SystemRequirements}),
    					success: function(data) {
						console.log("System Requirement - FR: " + data.trackerItems.total);
						$("#div010").text(data.trackerItems.total);
    					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
				// ** System Requirements - %Implemented 
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({queryString:"workItemResolution='Successful' AND tracker.id="+SystemRequirements}),
    					success: function(data) {
						p = (data.trackerItems.total/total).toFixed(2)*100 + "%";
						console.log("System Requirements - %Implemented: " + p);
						$("#div005").text(p);
    					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
				// ** System Requirements - %Decomposed - System Architecture Requirements
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({
						queryString:"downstreamReference = { tracker.id="+SystemArchitectureRequirements+" } AND tracker.id="+SystemRequirements}
					),
    					success: function(data) {
						p = (data.trackerItems.total/total).toFixed(2)*100 + "%";
						console.log("System Requirements - %Decomposed - System Architecture Requirements: " + p);
						$("#div011").text(p);
    					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
				// ** System Requirements - %Decomposed - Software Requirements
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({
						queryString:"downstreamReference = { tracker.id="+SoftwareRequirements+" } AND tracker.id="+SystemRequirements}
					),
    					success: function(data) {
						p = (data.trackerItems.total/total).toFixed(2)*100 + "%";
						console.log("System Requirements - %Decomposed - Software Requirements: " + p);
						$("#div016").text(p);
    					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
    			},
    			beforeSend: function (xhr) {
    				xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    			}
		});

		// System Architecture Requrements Matrix
		// ** System Architecture Requirements - Total
		$.ajax({
    			type: "POST",
    			url: "../rest/query/page/1",
    			contentType : 'application/json',
    			data: JSON.stringify({queryString:"tracker.id=" + SystemArchitectureRequirements}),
    			success: function(data) {
				var total = data.trackerItems.total;
				console.log("System Architecture Requirements - Total: "+total);
				$("#div013").text(total);
				// ** System Architecture Requirements - Approved
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({queryString:"status='Accepted' AND tracker.id=" + SystemArchitectureRequirements}),
    					success: function(data) {
						console.log("System Architecture Requirements - Approved: " + data.trackerItems.total);
						$("#div014").text(data.trackerItems.total);
						// %Approved
						p = (data.trackerItems.total/total).toFixed(2)*100 + "%";
						console.log("System Architecture Requirements - %Approved: " + p);
						$("#div015").text(p);
    					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
				// ** System Architecture Requirements - %Implemented 
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({queryString:"workItemResolution='Successful' AND tracker.id=" + SystemArchitectureRequirements}),
    					success: function(data) {
						p = (data.trackerItems.total/total).toFixed(2)*100 + "%";
						console.log("System Architecture Requirements - %Implemented: " + p);
						$("#div012").text(p);
    					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
				// ** System Architecture Requirements - %Decomposed
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({
						queryString:"downstreamReference = { tracker.id="+ SoftwareRequirements +" } AND tracker.id="+SystemArchitectureRequirements}
					),
    					success: function(data) {
						p = (data.trackerItems.total/total).toFixed(2)*100 + "%";
						console.log("System Architecture Requirements - %Decomposed: " + p);
						$("#div017").text(p);
    					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});

    			},
    			beforeSend: function (xhr) {
    				xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    			}
		});

		// Software Requirements - Matrix
		// ** Software Requirements - Total
		$.ajax({
    			type: "POST",
    			url: "../rest/query/page/1",
    			contentType : 'application/json',
    			data: JSON.stringify({queryString:"tracker.id=" + SoftwareRequirements}),
    			success: function(data) {
				var total = data.trackerItems.total;
				console.log("Software Requirements - Total: "+total);
				$("#div019").text(total);
				// ** Software Requirements - Approved
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({queryString:"status='Accepted' AND tracker.id=" + SoftwareRequirements}),
    					success: function(data) {
						console.log("Software Requirements - Approved: " + data.trackerItems.total);
						$("#div020").text(data.trackerItems.total);
						// Software Requirements - %Approved
						p = (data.trackerItems.total/total).toFixed(2)*100 + "%";
						console.log("Software Requirements - %Approved: " + p);
						$("#div021").text(p);
    					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
				// ** Software Requirements - %Implemented 
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({queryString:"workItemResolution='Successful' AND tracker.id=" + SoftwareRequirements}),
    					success: function(data) {
						p = (data.trackerItems.total/total).toFixed(2)*100 + "%";
						console.log("Software Requirements - %Implemented: " + p);
						$("#div018").text(p);
    					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
				// ** Software Requirements - %Decomposed - Software Architecture Requirememts
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({
						queryString:"downstreamReference = { tracker.id="+ SoftwareArchitectureRequirements +" } AND tracker.id="+SoftwareRequirements}
					),
    					success: function(data) {
						p = (data.trackerItems.total/total).toFixed(2)*100 + "%";
						console.log("Software Requirements - %Decomposed - Software Architecture Requirememts: " + p);
						$("#div022").text(p);
    					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
				// ** Software Requirements - %Decomposed - Software Detailed Design Units
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({
						queryString:"downstreamReference = { tracker.id="+ SoftwareDetailedDesignUnits +" } AND tracker.id="+SoftwareRequirements}
					),
    					success: function(data) {
						p = (data.trackerItems.total/total).toFixed(2)*100 + "%";
						console.log("Software Requirements - %Decomposed - Software Detailed Design Units: " + p);
						$("#div027").text(p);
    					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
				// ** Software Requirements - %Traced
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({
						queryString:"downstreamReference={tracker.id="+SoftwareQualificationTests+"} AND tracker.id=" + SoftwareRequirements
					}),
    					success: function(data) {
						p = (data.trackerItems.total/total).toFixed(2)*100 + "%";
						console.log("Software Requirements - %Traced: " + p);
						$("#div037").text(p);
    					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});

    			},
    			beforeSend: function (xhr) {
    				xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    			}
		});

		// Software Architecture Requirements - Matrix
		// ** Software Architecture Requirements - Total
		$.ajax({
    			type: "POST",
    			url: "../rest/query/page/1",
    			contentType : 'application/json',
    			data: JSON.stringify({queryString:"tracker.id=" + SoftwareArchitectureRequirements}),
    			success: function(data) {
				var total = data.trackerItems.total;
				console.log("Software Architecture Requirements - Total: "+total);
				$("#div024").text(total);
				// ** Software Architecture Requirements - Approved
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({queryString:"status='Accepted' AND tracker.id=" + SoftwareArchitectureRequirements}),
    					success: function(data) {
						console.log("Software Architecture Requirements - Approved: " + data.trackerItems.total);
						$("#div025").text(data.trackerItems.total);
						// Software Architecture Requirements - %Approved
						p = (data.trackerItems.total/total).toFixed(2)*100 + "%";
						console.log("Software Architecture Requirements - %Approved: " + p);
						$("#div026").text(p);
    					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
				// ** Software Architecture Requirements - %Implemented 
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({queryString:"workItemResolution='Successful' AND tracker.id=" + SoftwareArchitectureRequirements}),
    					success: function(data) {
						p = (data.trackerItems.total/total).toFixed(2)*100 + "%";
						console.log("Software Architecture Requirements - %Implemented: " + p);
						$("#div023").text(p);
    					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
				// ** Software Architecture Requirements - %Decomposed
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({
						queryString:"downstreamReference={tracker.id="+SoftwareDetailedDesignUnits+"} AND tracker.id="+SoftwareArchitectureRequirements}
					),
    					success: function(data) {
						p = (data.trackerItems.total/total).toFixed(2)*100 + "%";
						console.log("Software Architecture Requirements - %Decomposed: " + p);
						$("#div028").text(p);
    					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
				// ** Software Architecture Requirements - %Traced
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({
						queryString:"downstreamReference={tracker.id="+SoftwareIntegrationTests+"} AND tracker.id=" + SoftwareArchitectureRequirements
					}),
    					success: function(data) {
						p = (data.trackerItems.total/total).toFixed(2)*100 + "%";
						console.log("Software Architecture Requirements - %Traced: " + p);
						$("#div038").text(p);
    					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
    			},
    			beforeSend: function (xhr) {
    				xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    			}
		}); 
		
		// Software Details Design Units - Matrix
		// ** Software Details Design Units - Total
		$.ajax({
    			type: "POST",
    			url: "../rest/query/page/1",
    			contentType : 'application/json',
    			data: JSON.stringify({queryString:"tracker.id=" + SoftwareDetailedDesignUnits}),
    			success: function(data) {
				var total = data.trackerItems.total;
				console.log("Software Details Design Units - Total: "+total);
				$("#div030").text(total);
				// ** Software Detailed Design Units - Approved
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({queryString:"status='Accepted' AND tracker.id=" + SoftwareDetailedDesignUnits}),
    					success: function(data) {
						console.log("Software Detailed Design Units - Approved: " + data.trackerItems.total);
						$("#div031").text(data.trackerItems.total);
						// Software Detailed Design Units - %Approved
						p = (data.trackerItems.total/total).toFixed(2)*100 + "%";
						console.log("Software Detailed Design Units - %Approved: " + p);
						$("#div032").text(p);
    					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
				// ** Software Detailed Design Units - %Implemented 
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({queryString:"workItemResolution='Successful' AND tracker.id=" + SoftwareDetailedDesignUnits}),
    					success: function(data) {
						p = (data.trackerItems.total/total).toFixed(2)*100 + "%";
						console.log("Software Detailed Design Units - %Implemented: " + p);
						$("#div029").text(p);
    					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
				// ** Software Detailed Design Units - %Traced
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({
						queryString:"downstreamReference={tracker.id="+SoftwareUnitTests+"} AND tracker.id=" + SoftwareDetailedDesignUnits
					}),
    					success: function(data) {
						p = (data.trackerItems.total/total).toFixed(2)*100 + "%";
						console.log("Software Detailed Design Units - %Traced: " + p);
						$("#div039").text(p)
    					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
    			},
    			beforeSend: function (xhr) {
    				xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    			}
		});

		// Software Unit Tests Matrix
		// ** Software Unit Tests Matrix - Total 
		$.ajax({
    			type: "POST",
    			url: "../rest/query/page/1",
    			contentType : 'application/json',
    			data: JSON.stringify({queryString:"category='Software Unit Test Specification' AND tracker.id="+SoftwareUnitTests}),
    			success: function(data) {
				var total = data.trackerItems.total;
				console.log("Software Unit Tests - Total: "+total);
				$("#div074").text(total);
				// ** Software Unit Tests - Passed
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({
						queryString:"resolution='Passed' AND category='Software Unit Test Specification' AND tracker.id="+SoftwareUnitTests
					}),
    					success: function(data) {
						console.log("Software Unit Tests - Passed: " + data.trackerItems.total);
						$("#div075").text(data.trackerItems.total);
						// %Passed
						p = (data.trackerItems.total/total).toFixed(2)*100 + "%";
						console.log("Software Unit Tests - %Passed: " + p);
						$("#div078").text(p);
    					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
				// ** Software Unit Tests - Failed 
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({
						queryString:"resolution='Failed' AND category='Software Unit Test Specification' AND tracker.id="+SoftwareUnitTests
					}),
    					success: function(data) {
						console.log("Software Unit Tests - Failed: " + data.trackerItems.total);
						$("#div076").text(data.trackerItems.total);
    					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
				// ** Software Unit Tests - Not Tested 
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({
						queryString:"resolution IS NULL AND category='Software Unit Test Specification' AND tracker.id="+SoftwareUnitTests
					}),
    					success: function(data) {
						console.log("Software Unit Tests - Not Tested: " + data.trackerItems.total);
						$("#div077").text(data.trackerItems.total);
    					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
    			},
    			beforeSend: function (xhr) {
    				xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    			}
		});

		// Software Integration Tests Matrix
		// ** Software Integration Tests Matrix - Total 
		$.ajax({
    			type: "POST",
    			url: "../rest/query/page/1",
    			contentType : 'application/json',
    			data: JSON.stringify({queryString:"category='Software Integration Test Specification' AND tracker.id="+SoftwareIntegrationTests}),
    			success: function(data) {
				var total = data.trackerItems.total;
				console.log("Software Integration Tests - Total: "+total);
				$("#div069").text(total);
				// ** Software Integration Tests - Passed
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({
						queryString:"resolution='Passed' AND category='Software Integration Test Specification' AND tracker.id="+SoftwareIntegrationTests
					}),
    					success: function(data) {
						console.log("Software Integration Tests - Passed: " + data.trackerItems.total);
						$("#div070").text(data.trackerItems.total);
						// %Passed
						p = (data.trackerItems.total/total).toFixed(2)*100 + "%";
						console.log("Software Integration Tests - %Passed: " + p);
						$("#div073").text(p);
    					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
				// ** Software Integration Tests - Failed 
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({
						queryString:"resolution='Failed' AND category='Software Integration Test Specification' AND tracker.id="+SoftwareIntegrationTests
					}),
    					success: function(data) {
						console.log("Software Integration Tests - Failed: " + data.trackerItems.total);
						$("#div071").text(data.trackerItems.total)
    					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
				// ** Software Integration Tests - Not Tested 
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({
						queryString:"resolution IS NULL AND category='Software Integration Test Specification' AND tracker.id="+SoftwareIntegrationTests
					}),
    					success: function(data) {
						console.log("Software Integration Tests - Not Tested: " + data.trackerItems.total);
						$("#div072").text(data.trackerItems.total);
    					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
    			},
    			beforeSend: function (xhr) {
    				xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    			}
		});

		// Software Qualification Tests Matrix
		// ** Software Qualification Tests Matrix - Total 
		$.ajax({
    			type: "POST",
    			url: "../rest/query/page/1",
    			contentType : 'application/json',
    			data: JSON.stringify({queryString:"category='Software Qualification Test Specification' AND tracker.id="+SoftwareQualificationTests}),
    			success: function(data) {
				var total = data.trackerItems.total;
				console.log("Software Qualification Tests - Total: "+total);
				$("#div064").text(total);
				// ** Software Qualification Tests - Passed
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({
						queryString:"resolution='Passed' AND category='Software Qualification Test Specification' AND tracker.id="+SoftwareQualificationTests
					}),
    					success: function(data) {
						console.log("Software Qualification Tests - Passed: " + data.trackerItems.total);
						$("#div065").text(data.trackerItems.total);
						// %Passed
						p = (data.trackerItems.total/total).toFixed(2)*100 + "%";
						console.log("Software Qualification Tests - %Passed: " + p);
						$("#div068").text(p);
    					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
				// ** Software Qualification Tests - Failed 
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({
						queryString:"resolution='Failed' AND category='Software Qualification Test Specification' AND tracker.id="+SoftwareQualificationTests
					}),
    					success: function(data) {
						console.log("Software Qualification Tests - Failed: " + data.trackerItems.total);
						$("#div066").text(data.trackerItems.total);
    					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
				// ** Software Qualification Tests - Not Tested 
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({
						queryString:"resolution IS NULL AND category='Software Qualification Test Specification' AND tracker.id="+SoftwareQualificationTests
					}),
    					success: function(data) {
						console.log("Software Qualification Tests - Not Tested: " + data.trackerItems.total);
						$("#div067").text(data.trackerItems.total);
    					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
    			},
    			beforeSend: function (xhr) {
    				xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    			}
		});
		
		// SYIT Matrix
		// ** SYIT Req - Total
		$.ajax({
    			type: "POST",
    			url: "../rest/query/page/1",
    			contentType : 'application/json',
    			data: JSON.stringify({
				queryString:`'${SystemArchitectureRequirements_VerificationMethod}'='SYIT' AND tracker.id=${SystemArchitectureRequirements}`
			}),
    			success: function(data) {
				var total = data.trackerItems.total;
				console.log("SYIT Req - Total: "+total);
				$("#div052").text(total);
				// ** SYIT Req - %Traced
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({
						queryString:`'${SystemArchitectureRequirements_VerificationMethod}'='SYIT' AND tracker.id=${SystemArchitectureRequirements} AND downstreamReference={tracker.id=${SystemValidationSpecifications}}`
					}),
    					success: function(data) {
						p = (data.trackerItems.total/total).toFixed(2)*100 + "%";
						console.log("SYIT Req - %Traced: " + p);
						$("#div035").text(p);
					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
			},
    			beforeSend: function (xhr) {
    				xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    			}
		});
		// ** SYIT Tests - Total
		$.ajax({
    			type: "POST",
    			url: "../rest/query/page/1",
    			contentType : 'application/json',
    			data: JSON.stringify({
				queryString:`'${SystemValidationSpecifications_VerificationMethod}'='SYIT' AND tracker.id=${SystemValidationSpecifications}`
			}),
    			success: function(data) {
				var total = data.trackerItems.total;
				console.log("SYIT Tests - Total: "+total);
				$("#div053").text(total);
				// ** SYIT Tests - Passed
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({
						queryString:`'${SystemValidationSpecifications_VerificationMethod}'='SYIT' AND resolution='OK' AND tracker.id=${SystemValidationSpecifications}`
					}),
    					success: function(data) {
						console.log("SYIT Tests - Passed: "+data.trackerItems.total);
						$("#div054").text(data.trackerItems.total);
						// ** SYIT Tests - %Passed
						p = (data.trackerItems.total/total).toFixed(2)*100 + "%";
						console.log("SYIT Tests - %Passed: " + p);
						$("#div062").text(p);
					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
				// ** SYIT Tests - Failed
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({
						queryString:`'${SystemValidationSpecifications_VerificationMethod}'='SYIT' AND resolution='Not OK' AND tracker.id=${SystemValidationSpecifications}`
					}),
    					success: function(data) {
						console.log("SYIT Tests - Failed: "+data.trackerItems.total);
						$("#div055").text(data.trackerItems.total);
						
					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
				// ** SYIT Tests - Not Tested
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({
						queryString:`'${SystemValidationSpecifications_VerificationMethod}'='SYIT' AND resolution IS NULL AND tracker.id=${SystemValidationSpecifications}`
					}),
    					success: function(data) {
						console.log("SYIT Tests - Not Tested: "+data.trackerItems.total);
						$("#div056").text(data.trackerItems.total);
						
					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
			},
    			beforeSend: function (xhr) {
    				xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    			}
		});
		
		// Non-SYIT Matrix
		// ** Non-SYIT Req - Total
		$.ajax({
    			type: "POST",
    			url: "../rest/query/page/1",
    			contentType : 'application/json',
    			data: JSON.stringify({
				queryString:`'${SystemArchitectureRequirements_VerificationMethod}'='Non-SYIT' AND tracker.id=${SystemArchitectureRequirements}`
			}),
    			success: function(data) {
				var total = data.trackerItems.total;
				console.log("Non-SYIT Req - Total: "+total);
				$("#div057").text(total);
				// ** Non-SYIT Req - %Traced
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({
						queryString:`'${SystemArchitectureRequirements_VerificationMethod}'='Non-SYIT' AND tracker.id=${SystemArchitectureRequirements} AND downstreamReference={tracker.id=${SystemValidationSpecifications}}`
					}),
    					success: function(data) {
						p = (data.trackerItems.total/total).toFixed(2)*100 + "%";
						console.log("Non-SYIT Req - %Traced: " + p);
						$("#div036").text(p);
					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
			},
    			beforeSend: function (xhr) {
    				xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    			}
		});
		// ** Non-SYIT Tests - Total
		$.ajax({
    			type: "POST",
    			url: "../rest/query/page/1",
    			contentType : 'application/json',
    			data: JSON.stringify({
				queryString:`'${SystemValidationSpecifications_VerificationMethod}'='Non-SYIT' AND tracker.id=${SystemValidationSpecifications}`
			}),
    			success: function(data) {
				var total = data.trackerItems.total;
				console.log("Non-SYIT Tests - Total: "+total);
				$("#div058").text(total);
				// ** Non-SYIT Tests - Passed
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({
						queryString:`'${SystemValidationSpecifications_VerificationMethod}'='Non-SYIT' AND resolution='OK' AND tracker.id=${SystemValidationSpecifications}`
					}),
    					success: function(data) {
						console.log("Non-SYIT Tests - Passed: "+data.trackerItems.total);
						$("#div059").text(data.trackerItems.total);
						// ** Non-SYIT Tests - %Passed
						p = (data.trackerItems.total/total).toFixed(2)*100 + "%";
						console.log("Non-SYIT Tests - %Passed: " + p);
						$("#div063").text(p);
					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
				// ** Non-SYIT Tests - Failed
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({
						queryString:`'${SystemValidationSpecifications_VerificationMethod}'='Non-SYIT' AND resolution='Not OK' AND tracker.id=${SystemValidationSpecifications}`
					}),
    					success: function(data) {
						console.log("Non-SYIT Tests - Failed: "+data.trackerItems.total);
						$("#div060").text(data.trackerItems.total);
						
					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
				// ** Non-SYIT Tests - Not Tested
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({
						queryString:`'${SystemValidationSpecifications_VerificationMethod}'='Non-SYIT' AND resolution IS NULL AND tracker.id=${SystemValidationSpecifications}`
					}),
    					success: function(data) {
						console.log("Non-SYIT Tests - Not Tested: "+data.trackerItems.total);
						$("#div061").text(data.trackerItems.total);
						
					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
			},
    			beforeSend: function (xhr) {
    				xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    			}
		});
		
		// ITV Matrix
		// ** ITV Req - Total
		$.ajax({
    			type: "POST",
    			url: "../rest/query/page/1",
    			contentType : 'application/json',
    			data: JSON.stringify({
				queryString:`'${SystemRequirements_VerificationMethod}'='ITV' AND tracker.id=${SystemRequirements}`
			}),
    			success: function(data) {
				var total = data.trackerItems.total;
				console.log("ITV Req - Total: "+total);
				$("#div040").text(total);
				// ** ITV Req - %Traced
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({
						queryString:`'${SystemRequirements_VerificationMethod}'='ITV' AND tracker.id=${SystemRequirements} AND downstreamReference={tracker.id=${SystemValidationSpecifications}}`
					}),
    					success: function(data) {
						p = (data.trackerItems.total/total).toFixed(2)*100 + "%";
						console.log("ITV Req - %Traced: " + p);
						$("#div033").text(p);
					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
			},
    			beforeSend: function (xhr) {
    				xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    			}
		});
		// ** ITV Tests - Total
		$.ajax({
    			type: "POST",
    			url: "../rest/query/page/1",
    			contentType : 'application/json',
    			data: JSON.stringify({
				queryString:`'${SystemValidationSpecifications_VerificationMethod}'='ITV' AND tracker.id=${SystemValidationSpecifications}`
			}),
    			success: function(data) {
				var total = data.trackerItems.total;
				console.log("ITV Tests - Total: "+total);
				$("#div041").text(total);
				// ** ITV Tests - Passed
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({
						queryString:`'${SystemValidationSpecifications_VerificationMethod}'='ITV' AND resolution='OK' AND tracker.id=${SystemValidationSpecifications}`
					}),
    					success: function(data) {
						console.log("ITV Tests - Passed: "+data.trackerItems.total);
						$("#div042").text(data.trackerItems.total);
						// ** ITV Tests - %Passed
						p = (data.trackerItems.total/total).toFixed(2)*100 + "%";
						console.log("ITV Tests - %Passed: " + p);
						$("#div050").text(p);
					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
				// ** ITV Tests - Failed
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({
						queryString:`'${SystemValidationSpecifications_VerificationMethod}'='ITV' AND resolution='Not OK' AND tracker.id=${SystemValidationSpecifications}`
					}),
    					success: function(data) {
						console.log("ITV Tests - Failed: "+data.trackerItems.total);
						$("#div043").text(data.trackerItems.total);
						
					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
				// ** ITV Tests - Not Tested
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({
						queryString:`'${SystemValidationSpecifications_VerificationMethod}'='ITV' AND resolution IS NULL AND tracker.id=${SystemValidationSpecifications}`
					}),
    					success: function(data) {
						console.log("ITV Tests - Not Tested: "+data.trackerItems.total);
						$("#div044").text(data.trackerItems.total);
						
					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
			},
    			beforeSend: function (xhr) {
    				xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    			}
		});

		// Non-ITV Matrix
		// ** Non-ITV Req - Total
		$.ajax({
    			type: "POST",
    			url: "../rest/query/page/1",
    			contentType : 'application/json',
    			data: JSON.stringify({
				queryString:`'${SystemRequirements_VerificationMethod}'='Non-ITV' AND tracker.id=${SystemRequirements}`
			}),
    			success: function(data) {
				var total = data.trackerItems.total;
				console.log("Non-ITV Req - Total: "+total);
				$("#div045").text(total);
				// ** Non-ITV Req - %Traced
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({
						queryString:`'${SystemRequirements_VerificationMethod}'='Non-ITV' AND tracker.id=${SystemRequirements} AND downstreamReference={tracker.id=${SystemValidationSpecifications}}`
					}),
    					success: function(data) {
						p = (data.trackerItems.total/total).toFixed(2)*100 + "%";
						console.log("Non-ITV Req - %Traced: " + p);
						$("#div034").text(p);
					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
			},
    			beforeSend: function (xhr) {
    				xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    			}
		});
		// ** Non-ITV Tests - Total
		$.ajax({
    			type: "POST",
    			url: "../rest/query/page/1",
    			contentType : 'application/json',
    			data: JSON.stringify({
				queryString:`'${SystemValidationSpecifications_VerificationMethod}'='Non-ITV' AND tracker.id=${SystemValidationSpecifications}`
			}),
    			success: function(data) {
				var total = data.trackerItems.total;
				console.log("Non-ITV Tests - Total: "+total);
				$("#div046").text(total);
				// ** Non-ITV Tests - Passed
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({
						queryString:`'${SystemValidationSpecifications_VerificationMethod}'='Non-ITV' AND resolution='OK' AND tracker.id=${SystemValidationSpecifications}`
					}),
    					success: function(data) {
						console.log("Non-ITV Tests - Passed: "+data.trackerItems.total);
						$("#div047").text(data.trackerItems.total);
						// ** Non-ITV Tests - %Passed
						p = (data.trackerItems.total/total).toFixed(2)*100 + "%";
						console.log("Non-ITV Tests - %Passed: " + p);
						$("#div051").text(p);
					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
				// ** Non-ITV Tests - Failed
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({
						queryString:`'${SystemValidationSpecifications_VerificationMethod}'='Non-ITV' AND resolution='Not OK' AND tracker.id=${SystemValidationSpecifications}`
					}),
    					success: function(data) {
						console.log("Non-ITV Tests - Failed: "+data.trackerItems.total);
						$("#div048").text(data.trackerItems.total);
						
					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
				// ** Non-ITV Tests - Not Tested
				$.ajax({
    					type: "POST",
    					url: "../rest/query/page/1",
    					contentType : 'application/json',
    					data: JSON.stringify({
						queryString:`'${SystemValidationSpecifications_VerificationMethod}'='Non-ITV' AND resolution IS NULL AND tracker.id=${SystemValidationSpecifications}`
					}),
    					success: function(data) {
						console.log("Non-ITV Tests - Not Tested: "+data.trackerItems.total);
						$("#div049").text(data.trackerItems.total);
						
					},
    					beforeSend: function (xhr) {
    						xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    					}
				});
			},
    			beforeSend: function (xhr) {
    				xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    			}
		});

		// Problems Matrix
		// ** Problems (Functional + Non Functional) - Total
		$.ajax({
    			type: "POST",
    			url: "../rest/query/page/1",
    			contentType : 'application/json',
    			data: JSON.stringify({
				queryString:"tracker.id="+Problems+" AND category IN ('Functional','Non Functional')"
			}),
    			success: function(data) {
				console.log("Problems (Functional + Non Functional) - Total: "+data.trackerItems.total);
				$("#div079").text(data.trackerItems.total);
			},
    			beforeSend: function (xhr) {
    				xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    			}
		});
		// ** Problems (Functional Defects) Customer Reqorted - Total
		$.ajax({
    			type: "POST",
    			url: "../rest/query/page/1",
    			contentType : 'application/json',
    			data: JSON.stringify({
				queryString:"tracker.id="+Problems+" AND category='Functional Defects' AND '"+Problems_Customer+"'='Yes'"
			}),
    			success: function(data) {
				console.log("Problems (Functional Defects) Customer Reqorted - Total: "+data.trackerItems.total);
				$("#div080").text(data.trackerItems.total);
			},
    			beforeSend: function (xhr) {
    				xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    			}
		});
		// ** Problems (Functional Defects) - Total
		$.ajax({
    			type: "POST",
    			url: "../rest/query/page/1",
    			contentType : 'application/json',
    			data: JSON.stringify({
				queryString:"tracker.id="+Problems+" AND category IN ('Functional Defects')"
			}),
    			success: function(data) {
				console.log("Problems (Functional Defects) - Total: "+data.trackerItems.total);	
				$("#div081").text(data.trackerItems.total);
			},
    			beforeSend: function (xhr) {
    				xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    			}
		});
		// ** Problems (Functional) - Opened
		$.ajax({
    			type: "POST",
    			url: "../rest/query/page/1",
    			contentType : 'application/json',
    			data: JSON.stringify({
				queryString:"tracker.id="+Problems+" AND category IN ('Functional') AND workItemStatus IN ('Unset','InProgress','Resolved')"
			}),
    			success: function(data) {
				console.log("Problems (Functional) - Opened: "+data.trackerItems.total);
				$("#div082").text(data.trackerItems.total);
			},
    			beforeSend: function (xhr) {
    				xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    			}
		});
		// ** Problems (Customer Reported) - Opened
		$.ajax({
    			type: "POST",
    			url: "../rest/query/page/1",
    			contentType : 'application/json',
    			data: JSON.stringify({
				queryString:"tracker.id="+Problems+" AND workItemStatus IN ('Unset','InProgress','Resolved') AND '"+Problems_Customer+"'='Yes'"
			}),
    			success: function(data) {
				console.log("Problems (Customer Reported) - Opened: "+data.trackerItems.total);	
				$("#div083").text(data.trackerItems.total);
			},
    			beforeSend: function (xhr) {
    				xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    			}
		});
		// ** Problems (Functional Defects) UBV20 - Opend
		$.ajax({
    			type: "POST",
    			url: "../rest/query/page/1",
    			contentType : 'application/json',
    			data: JSON.stringify({
				queryString:"tracker.id="+Problems+" AND workItemStatus IN ('Unset','InProgress','Resolved') AND category='Functional Defects' AND '"+Problems_UBV+"'='20'"
			}),
    			success: function(data) {
				console.log("Problems (Functional Defects) UBV20 - Opend: "+data.trackerItems.total);
				$("#div084").text(data.trackerItems.total);
			},
    			beforeSend: function (xhr) {
    				xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    			}
		});
		// ** Problems (Functional Defects) UBV13 - Opend
		$.ajax({
    			type: "POST",
    			url: "../rest/query/page/1",
    			contentType : 'application/json',
    			data: JSON.stringify({
				queryString:"tracker.id="+Problems+" AND workItemStatus IN ('Unset','InProgress','Resolved') AND category='Functional Defects' AND '"+Problems_UBV+"'='13'"
			}),
    			success: function(data) {
				console.log("Problems (Functional Defects) UBV13 - Opend: "+data.trackerItems.total);
				$("#div085").text(data.trackerItems.total);
			},
    			beforeSend: function (xhr) {
    				xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    			}
		});

		// Peer Reviews Matrix
		// ** Peer Reviews planned - Total
		$.ajax({
    			type: "POST",
    			url: "../rest/query/page/1",
    			contentType : 'application/json',
    			data: JSON.stringify({
				queryString:"tracker.id="+PeerReviews+" AND status='Open'"
			}),
    			success: function(data) {
				console.log("Peer Reviews planned - Total: "+data.trackerItems.total);
				$("#div086").text(data.trackerItems.total);
			},
    			beforeSend: function (xhr) {
    				xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    			}
		});
		// ** Peer Reviews closed - Total
		$.ajax({
    			type: "POST",
    			url: "../rest/query/page/1",
    			contentType : 'application/json',
    			data: JSON.stringify({
				queryString:"tracker.id="+PeerReviews+" AND status='Done'"
			}),
    			success: function(data) {
				console.log("Peer Reviews closed - Total: "+data.trackerItems.total);
				$("#div087").text(data.trackerItems.total);
			},
    			beforeSend: function (xhr) {
    				xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    			}
		});
		// ** Peer Reviews overdue - Total
		$.ajax({
    			type: "POST",
    			url: "../rest/query/page/1",
    			contentType : 'application/json',
    			data: JSON.stringify({
				queryString:`tracker.id=${PeerReviews} AND '${PeerReviews_Overdue}'='Yes'`
			}),
    			success: function(data) {
				console.log("Peer Reviews overdue - Total: "+data.trackerItems.total);
				$("#div088").text(data.trackerItems.total);
			},
    			beforeSend: function (xhr) {
    				xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
    			}
		});

		//
